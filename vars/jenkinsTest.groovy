import java.io.*

def call(Map map, env) {
    println('【开始进行构建】')
    println(env.TEST1)
    return

    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-dev'
                label map.get('appName')
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom baseTemplateName()
                yaml jenkinsTemplate(map)
            }
        }

        environment {
            tags = "${map.REPO_URL}"
//            execComand = "${map.execComand}"
        }

        stages {
            stage('Exec Command') {
                steps {
                    container('mvn') {
//                        sh '$execComand'
                        sh 'mvn deploy'
                    }
                }
            }

            stage('Check Package') {
                steps {
                    container('mvn') {
                        sh 'du -sh target/cwd-2.0-beta.jar'
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
  - name: mvn
    image: $ansibleImage
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    volumeMounts:
    - name: data1
      mountPath: /root/.m2
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
  - name: data1
    hostPath:
      path: /data1/jenkins/confluence
'''
    def binding = [
            'ansibleImage' :  map.containsKey('ansibleImage') ? map.get('ansibleImage'): 'docker.dm-ai.cn/devops/base-image-mvn:0.01',
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

