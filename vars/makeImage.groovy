
import java.io.*

def call(Map map, env) {

    println('【开始进行构建】')
    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-test'
                label map.get('appName')
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom baseTemplateName()
                yaml jenkinsTemplate()
            }
        }

        options {
            timeout(time:1, unit: 'HOURS')
            retry(2)
        }

        environment {
            tags = "${map.REPO_URL}"
            dockerFile = dockerFileContent(map, env)
            dockerComposeFile = dockerComposeFile(map, env)
        }

        stages {
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

def jenkinsTemplate() {
    return """
apiVersion: v1
kind: Pod
metadata:
  name: jenkinsTemplate
  namespace: devops
spec:
  imagePullSecrets:
  - name: regsecret
  containers: 
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
        memory: 4000Mi
        cpu: 2000m
      requests:
        cpu: 1000m
        memory: 2000Mi
  volumes:
  - name: sock
    hostPath:
      path: /var/run/docker.sock      
"""
}


def dockerFileContent(map, env) {
    switch (map.get('appName')) {
        case 'base-image-exec-ansible' :
            switch (env.BRANCH_NAME) {
                case 'k8s-deploy-dev':
                    return '''
FROM docker.dm-ai.cn/devops/base-image-exec-ansible:master-0.0.1
RUN yum install -y git
ADD ./bin /etc/ansible/bin
'''
                default:
                    return '''
FROM williamyeh/ansible:master-centos7
RUN mkdir -p /root/.ssh
COPY ./id_rsa /root/.ssh/id_rsa
COPY ./id_rsa.pub /root/.ssh/id_rsa.pub
RUN chmod 0600 /root/.ssh/id_rsa && eval `ssh-agent` && ssh-add ~/.ssh/id_rsa
'''
            }
    }
//
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

def emailBody(env, buildResult, Map map) {
    def text = '''Job build $buildResult Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline
'''
    def binding = [
            'jobName' :  env.JOB_NAME.split("/")[0],
            'branchName' : env.BRANCH_NAME,
            'buildNumber' : env.BUILD_NUMBER,
            'buildResult': buildResult,
    ]
    return simpleTemplate(text, binding)
}

def simpleTemplate(text, binding) {
    def engine = new groovy.text.SimpleTemplateEngine()
    def template = engine.createTemplate(text).make(binding)
    return template.toString()
}

