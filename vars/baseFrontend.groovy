import java.io.*

def call(Map map, env) {

    // 临时的，后面再进行抽取
    def globalConfig = [
            'frontend-test' : [
                    'nodePort': '31377'
            ],
            'work-attendance-frontend': [
                    'nodePort': '30800',
                    'namespace': 'mis'
            ],
            'mis-admin-frontend': [
                    'nodePort': '30090',
                    'namespace': 'mis'
            ],
            'org-frontend': [
                    'nodePort': '30091',
                    'namespace': 'mis'
            ]
    ]

    map.put('globalConfig', globalConfig)

    println('【开始进行构建】')
    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-test'
                label 'yarnTemplate'
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom baseTemplateName()
                yaml yarnTemplate()
            }
        }

        options {
            timeout(time:1, unit: 'HOURS')
            retry(2)
        }

        environment {
            tags = "${map.REPO_URL}"
            dockerFile = dockerFileContent()
            dockerComposeFile = dockerComposeFile(map)
            kubernetesContentDeployFile = kubernetesContent(map)
        }

        stages {
            stage('Compile') {
                when {
                    branch map.buildBranch
                }

                steps {
                    container('compile') {
                        println("【开始进行编译】")
                        sh '''
                            npm config set registry=http://192.168.3.13:8081/repository/npm/ && npm install && npm run build
                           '''
                    }
                }
            }

            stage('Make image') {
                when {
                    branch map.buildBranch
                }
                steps {
                    container('docker-compose') {
                        println('【创建Dockerfile】')
                        sh 'echo "${dockerFile}" > Dockerfile'

                        println('【创建docker-compose】')
                        sh 'echo -e "${dockerComposeFile}" > docker-compose.yml'

                        println('【Make image】')
                        sh 'docker-compose build'

                        println('【Push image】')
                        sh 'docker-compose push'
                    }
                }
            }

            stage('Deploy') {
                when {
                    branch map.buildBranch
                }
                steps {
                    container('kubectl') {
                        println('【创建k8s部署文件】')
                        sh 'echo "${kubernetesContentDeployFile}" > Deploy-k8s.yml'
                        println('【执行部署】')
                        sh 'kubectl delete -f Deploy-k8s.yml'
                        sh 'sleep 5'
                        sh 'kubectl apply -f Deploy-k8s.yml'
                    }
                }
            }
        }

        post {
            always {
                echo "over!!"
            }

            failure {
                script {
                    emailext (
                            body: emailBody(env, 'success', map),
                            subject: 'Jenkins build faild info',
                            to: "${map.emailAddress}"
                    )
                }
            }

            success {
                script {
                    emailext (
                            body: emailBody(env, 'success', map),
                            subject: 'Jenkins build success info',
                            to: "${map.emailAddress}"
                    )
                }
            }
        }

    }
}

def baseTemplateName() {
    return 'base-template'
}

def yarnTemplate() {
    return """
apiVersion: v1
kind: Pod
metadata:
  name: yarnTemplate
  namespace: devops
spec:
  imagePullSecrets:
  - name: regsecret
  containers:
  - name: kubectl 
    image: docker.dm-ai.cn/devops/base-image-kubectl:test-0.01
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn  
    command:
    - "/bin/sh"
    - "-c"
    args:
    - "cat"
    tty: true
    resources:
      limits:
        memory: 300Mi
        cpu: 200m
      requests:
        cpu: 100m
        memory: 200Mi  
  - name: docker-compose
    image: docker.dm-ai.cn/devops/base-image-docker-compose:0.04
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn  
    volumeMounts:
    - name: sock
      mountPath: /var/run/docker.sock
    command:
    - "/bin/sh"
    - "-c"
    args:
    - "cat"
    tty: true
    resources:
      limits:
        memory: 600Mi
        cpu: 400m
      requests:
        cpu: 200m
        memory: 400Mi
  - name: compile
    image: docker.dm-ai.cn/devops/base-image-compile-frontend:0.03
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    command:
    - "/bin/sh"
    - "-c"
    args:
    - "cat"
    tty: true
    resources:
      limits:
        memory: 4000Mi
        cpu: 2000m
      requests:
        cpu: 1500m
        memory: 3000Mi
  volumes:
  - name: sock
    hostPath:
      path: /var/run/docker.sock   
  nodeSelector:
    makeenv: jenkins         
"""
}


def dockerFileContent() {
    return '''
FROM nginx
ENV TZ=Asia/Shanghai
ADD dist /usr/share/nginx/html
ADD nginx.conf /etc/nginx/conf.d/default.conf
RUN ln -sf /dev/stdout /var/log/nginx/access.log
RUN ln -sf /dev/stderr /var/log/nginx/error.log
EXPOSE 80
ENTRYPOINT nginx -g "daemon off;"
'''
}

def dockerComposeFile(map) {
    def text = '''
version: "2"
services:
  service-docker-build:
    build: ./
    image: $dockerRegistryHost/$imageUrlPath:$imageTags
'''
    def binding = [
            'imageUrlPath' : map.imageUrlPath,
            'imageTags' : map.imageTags,
            'dockerRegistryHost' : map.dockerRegistryHost,
    ]

    return simpleTemplate(text, binding)
}

def kubernetesContent(map) {
    def text = '''
---
apiVersion: v1
kind: Service
metadata:
  labels:
    app: $appName
  name: $appName
  namespace: $namespace
spec:
  ports:
  - port: 80
    protocol: TCP
    targetPort: 80
    nodePort: $nodePort
  selector:
    app: $appName
  type: NodePort

---
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: $appName
  namespace: mis
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: $appName
    spec:
      imagePullSecrets:
      - name: regsecret
      containers:
      - name: service-prometheus
        image: $dockerRegistryHost/$imageUrlPath:$imageTags
        imagePullPolicy: Always #
        env: #指定容器中的环境变量
        - name: TZ
          value: Asia/Shanghai
        resources:
          limits:
            memory: 500Mi
          requests:
            cpu: 100m
            memory: 200Mi
        ports:
        - containerPort: 80
'''
    def binding = [
            'imageUrlPath' : map.imageUrlPath,
            'imageTags' : map.imageTags,
            'dockerRegistryHost' : map.dockerRegistryHost,
            'appName' : map.appName,
            'nodePort' : map.get('globalConfig').get(map.appName).get('nodePort'),
            'namespace': map.get('globalConfig').get(map.appName).get('namespace'),
    ]

    return simpleTemplate(text, binding)
}

def emailBody(env, buildResult, Map map) {
    def text = '''Job build $buildResult Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline
应用名称 $appCN :  $appurl
'''
    def binding = [
            'jobName' :  env.JOB_NAME.split("/")[0],
            'branchName' : env.BRANCH_NAME,
            'buildNumber' : env.BUILD_NUMBER,
            'buildResult': buildResult,
            'appurl' : 'http://192.168.3.140:' +  map.get('globalConfig').get(map.appName).get('nodePort'),
            'appCN' : map.get('appCN')
    ]
    return simpleTemplate(text, binding)
}

def simpleTemplate(text, binding) {
    def engine = new groovy.text.SimpleTemplateEngine()
    def template = engine.createTemplate(text).make(binding)
    return template.toString()
}
