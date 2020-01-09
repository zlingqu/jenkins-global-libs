
import java.io.*

def call(Map map, env) {
    // 临时的，后面再进行抽取
    println("开始构建")
    if (env.BRANCH_NAME != "master" && env.BRANCH_NAME != "dev") {
        return
    }

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
            'mis-admin-backend': [
                    'nodePort': '31500',
                    'namespace': 'mis',
                    'appPort': '5000'
            ]
    ]

    map.put('globalConfig', globalConfig)

    println('【开始执行pipeline】')
    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-test'
                label map.get('appName')
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom baseTemplateName()
                yaml nodeTemplate(env)
            }
        }

        options {
            timeout(time:1, unit: 'HOURS')
            retry(2)
        }

        environment {
            tags = "${map.REPO_URL}"
            dockerFile = dockerFileContent()
            dockerComposeFile = dockerComposeFile(map, env)
            kubernetesContentDeployFile = kubernetesContent(map, env)
        }

        stages {

            stage('Make image') {
                when { anyOf { branch 'master'; branch 'dev' } }


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
                when { anyOf { branch 'master'; branch 'dev' } }

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

def nodeTemplate(env) {
    def text = '''
apiVersion: v1
kind: Pod
metadata:
  name: nodeTemplate
  namespace: devops
spec:
  imagePullSecrets:
  - name: regsecret
  containers:
  - name: kubectl 
    image: $kubectl
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn  
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
    resources:
      limits:
        memory: 2000Mi
        cpu: 1000m
      requests:
        cpu: 500m
        memory: 1000Mi  
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
    - "sleep"
    args:
    - "1200"
    tty: true
    resources:
      limits:
        memory: 2000Mi
        cpu: 1000m
      requests:
        cpu: 500m
        memory: 1000Mi
  volumes:
  - name: sock
    hostPath:
      path: /var/run/docker.sock         
  nodeSelector:
    makeenv: jenkins                 
'''
    def binding = [
            'kubectl' :  getKubectImage(env.BRANCH_NAME)
    ]

    return simpleTemplate(text, binding)
}

def getKubectImage(branch) {
    if (branch == "master") {
        return 'docker.dm-ai.cn/devops/base-image-kubectl:0.01'
    }

    if (branch == "dev") {
        return 'docker.dm-ai.cn/devops/base-image-kubectl:test-0.01'
    }
}

def dockerFileContent() {
    return '''
FROM docker.dm-ai.cn/devops/node-10:0.0.1
WORKDIR /app
COPY package*.json ./
RUN npm config set registry http://nexus.dm-ai.cn/repository/npm/ && npm install
COPY . .
VOLUME ["/app/data"]
CMD [ "npm", "start" ]
'''
}

def dockerComposeFile(map, env) {
    def text = '''
version: "2"
services:
  service-docker-build:
    build: ./
    image: $dockerRegistryHost/$imageUrlPath:$branchName-$imageTags
'''
    def binding = [
            'imageUrlPath' : map.imageUrlPath,
            'imageTags' : map.imageTags,
            'dockerRegistryHost' : map.dockerRegistryHost,
            'branchName' : env.BRANCH_NAME
    ]

    return simpleTemplate(text, binding)
}

def kubernetesContent(map, env) {
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
  - port: $appPort
    protocol: TCP
    targetPort: $appPort
    nodePort: $nodePort
  selector:
    app: $appName
  type: NodePort

---
$configMapContent
---
apiVersion: apps/v1beta1
kind: Deployment
metadata:
  name: $appName
  namespace: $namespace
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
      - name: $appName
        image: $dockerRegistryHost/$imageUrlPath:$branchName-$imageTags
        imagePullPolicy: Always
        command:
        - npm
        args:
        - start
        env:
        - name: TZ
          value: Asia/Shanghai
        ports:
        - containerPort: $appPort
        volumeMounts:
        - name: myconf
          mountPath: /app/config.env
          subPath: config.env
        - name: data
          mountPath: /app/data
      volumes:
      - name: myconf
        configMap:
          name: $appName
      - name: data
$getData
'''
    def binding = [
            'imageUrlPath' : map.imageUrlPath,
            'imageTags' : map.imageTags,
            'dockerRegistryHost' : map.dockerRegistryHost,
            'appName' : map.appName,
            'nodePort' : map.get('globalConfig').get(map.appName).get('nodePort'),
            'namespace': map.get('globalConfig').get(map.appName).get('namespace'),
            'appPort': map.get('globalConfig').get(map.appName).get('appPort'),
            'configMapContent': getConfigContent(map.appName, env.BRANCH_NAME),
            'branchName': env.BRANCH_NAME,
            'getData': getDate(map.appName, env.BRANCH_NAME)
    ]

    return simpleTemplate(text, binding)
}

def getDate(appName, branchName) {
    if (appName == "mis-admin-backend" && branchName == "master") {
        return '''
        persistentVolumeClaim:
          claimName: mypvc
'''
    }

    if (appName == "mis-admin-backend" && branchName == "dev") {
        return '''
        hostPath:
           path: /data/mis
'''
    }

}

def getConfigContent(appName, branchName) {
    if (appName == "mis-admin-backend" && branchName == "master") {
        return '''
apiVersion: v1
kind: ConfigMap
metadata:
  name: mis-admin-backend
  namespace: mis
data:
  config.env: |-
    NODE_ENV=prod
    PORT=5000
    APP_NAME=admin

    # MongoDB配置
    MONGODB_CONNECTION="mongodb://dm-mis:c243419c3afc7ece77c@192.168.11.51:27500,192.168.12.51:27500,192.168.13.51:27500/dm-mis?authSource=dm-mis"

    # 默认每页显示条数
    PAGE_SIZE=10

    # LDAP服务器配置
    LDAP_URL="ldap://192.168.3.41:389"
    LDAP_BASE="dc=dmai,dc=com"
    LDAP_USER="cn=mis_bind,ou=apps,dc=dmai,dc=com"
    LDAP_PASSWD="Dm@imis19"
    LDAP_TIMEOUT=120

    # 微信请求相关配置
    WX_BASE_URL="https://qyapi.weixin.qq.com/cgi-bin"
    WX_REQUEST_TIMEOUT=3000
    WX_CORP_ID=ww399a98a04dfbcda4
    WX_CONTACT_SECRET=gZazZkwuoPcrmli8tu4-h6op6CTnL5o7LvoU8wEPuqA
    WX_APPROVAL_SECRET="hFw3-lNcqlD4ilT8YwnAJwk650sElWXyVi8n3EEsgDs"
'''
    }

    if (appName == "mis-admin-backend" && branchName == "dev") {
        return ''
    }
}

def emailBody(env, buildResult, Map map) {
    def text = '''Job build $buildResult Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline
应用名称 $appCN :  $appurl
'''
    def k8sAddress = "192.168.12.20"
    if (env.BRANCH_NAME != "master") {
        k8sAddress = "192.168.3.140"
    }

    def binding = [
            'jobName' :  env.JOB_NAME.split("/")[0],
            'branchName' : env.BRANCH_NAME,
            'buildNumber' : env.BUILD_NUMBER,
            'buildResult': buildResult,
            'appurl' : 'http://' + k8sAddress + ':' +  map.get('globalConfig').get(map.appName).get('nodePort'),
            'appCN' : map.get('appCN')
    ]
    return simpleTemplate(text, binding)
}

def simpleTemplate(text, binding) {
    def engine = new groovy.text.SimpleTemplateEngine()
    def template = engine.createTemplate(text).make(binding)
    return template.toString()
}
