
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
                yaml jenkinsTemplate(map)
            }
        }
//
//        options {
//            timeout(time:1, unit: 'HOURS')
//        }

        environment {
            tags = "${map.REPO_URL}"
            execComand = "${map.execComand}"
        }

        stages {
            stage('Exec Ansible') {
                steps {
                    container('ansible') {
                        withCredentials([usernamePassword(credentialsId: 'passwd-zs', passwordVariable: 'password', usernameVariable: 'username')]) {
                            sh 'source /etc/profile; git config --global http.sslVerify false ; git clone https://$username:$password@gitlab.dm-ai.cn/application-engineering/devops/ansible.git'
                            sh 'source /etc/profile; git config --global http.sslVerify false ; git clone https://$username:$password@gitlab.dm-ai.cn/application-engineering/devops/kubeasz.git /opt/kubeasz && cp -rp /opt/kubeasz/* /etc/ansible'
                        }
                        sh '$execComand'
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

def jenkinsTemplate(Map map) {
    def text = '''
apiVersion: v1
kind: Pod
metadata:
  name: jenkinsTemplate
  namespace: devops
spec:
  imagePullSecrets:
  - name: regsecret
  containers:
  - name: ansible
    image: $ansibleImage
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
        memory: 5000Mi
        cpu: 3000m
      requests:
        cpu: 2000m
        memory: 4000Mi
  nodeSelector:
    makeenv: cpu
'''
    def binding = [
            'ansibleImage' :  map.containsKey('ansibleImage') ? map.get('ansibleImage'): 'docker.dm-ai.cn/devops/base-image-exec-ansible:0.02',
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


