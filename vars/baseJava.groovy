import java.io.*

def call(Map map, env) {

    // 临时的，后面再进行抽取
    def globalConfig = [
            'dmai-confluence-plugin' : [
                    'nodePort': '31378'
            ]
    ]

    map.put('globalConfig', globalConfig)

    println('【开始进行构建】')
    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-online'
                label 'dmaiConfluencePluginTemplate'
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom baseTemplateName()
                yaml dmaiConfluencePluginTemplate()
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
            plugCommand = "${map.command}"
        }

        stages {
//            stage('Compile') {
//                steps {
//                    container('compile') {
//                        println("【开始进行编译】")
//                        sh '''
//                            npm config set registry=http://192.168.3.13:8081/repository/npm/ && npm install && npm run build
//                           '''
//                    }
//                }
//            }

            stage('Make image') {
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
                steps {
                    container('kubectl') {
                        println('【创建k8s部署文件】')
                        sh 'echo "${kubernetesContentDeployFile}" > Deploy-k8s.yml'
                        println('【执行部署】')
                        sh 'kubectl apply -f Deploy-k8s.yml'
                    }
                }
            }

            stage('plug') {
                steps {
                    container('plugin') {
                        println('【Plugin】') {
                            sh '$plugCommand'
                        }
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

def dmaiConfluencePluginTemplate() {
    return """
apiVersion: v1
kind: Pod
metadata:
  name: dmaiConfluencePluginTemplate
  namespace: devops
spec:
  imagePullSecrets:
  - name: regsecret
  containers:
  - name: kubectl 
    image: docker.dm-ai.cn/devops/base-image-kubectl:0.01
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
        memory: 1000Mi
        cpu: 800m
      requests:
        cpu: 400m
        memory: 600Mi
        
  - name: plugin
    image: docker.dm-ai.cn/devops/base-image-compiledmai-confluence-plugin:0.03
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn  
    volumeMounts:
    - name: sock
      mountPath: /var/run/docker.sock
    - name: dmai-confluence-plugin
      mountPath: /root/.m2
      subPath: jenkins_home/dmai-confluence-plugin       
    command:
    - "/bin/sh"
    - "-c"
    args:
    - "cat"
    tty: true
    resources:
      limits:
        memory: 1000Mi
        cpu: 800m
      requests:
        cpu: 400m
        memory: 600Mi        
  volumes:
  - name: sock
    hostPath:
      path: /var/run/docker.sock
  - name: dmai-confluence-plugin
    persistentVolumeClaim:
      claimName: mypvc         
"""
}


def dockerFileContent() {
    return '''
FROM docker.dm-ai.cn/devops/base-image-compiledmai-confluence-plugin:0.03
ENV TZ=Asia/Shanghai
ADD ./ /workspace/code
WORKDIR /workspace/code
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
  namespace: devops
spec:
  ports:
  - port: 1990
    protocol: TCP
    targetPort: 1990
    nodePort: $nodePort
  selector:
    app: $appName
  type: NodePort

---
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: $appName
  namespace: devops
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
      - name: server
        image: $dockerRegistryHost/$imageUrlPath:$imageTags
        imagePullPolicy: Always #
        command:
        - "/workspace/atlassian-plugin/bin/atlas-debug"
        env: #指定容器中的环境变量
        - name: TZ
          value: Asia/Shanghai
        resources:
          limits:
            memory: 2000Mi
          requests:
            cpu: 500m
            memory: 1000Mi
        ports:
        - containerPort: 1990
        volumeMounts:
        - name: dmai-confluence-plugin
          mountPath: /root/.m2
          subPath: jenkins_home/dmai-confluence-plugin        
      volumes:
      - name: dmai-confluence-plugin
        persistentVolumeClaim:
          claimName: mypvc     
'''
    def binding = [
            'imageUrlPath' : map.imageUrlPath,
            'imageTags' : map.serverImageTags,
            'dockerRegistryHost' : map.dockerRegistryHost,
            'appName' : map.appName,
            'nodePort' : map.get('globalConfig').get(map.appName).get('nodePort')
    ]

    return simpleTemplate(text, binding)
}

def emailBody(env, buildResult, Map map) {
    def text = '''Job build $buildResult Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline
App url addRess :  $appurl
'''
    def binding = [
            'jobName' :  env.JOB_NAME.split("/")[0],
            'branchName' : env.BRANCH_NAME,
            'buildNumber' : env.BUILD_NUMBER,
            'buildResult': buildResult,
            'appurl' : 'http://192.168.3.140:' +  map.get('globalConfig').get(map.appName).get('nodePort')
    ]
    return simpleTemplate(text, binding)
}

def simpleTemplate(text, binding) {
    def engine = new groovy.text.SimpleTemplateEngine()
    def template = engine.createTemplate(text).make(binding)
    return template.toString()
}

