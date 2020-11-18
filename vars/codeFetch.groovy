def call(Map map, env) {
    println("this is a test")

    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-dev'
                label map.get('appName')
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom baseTemplateName()
                yaml compileNodeExporterTemplate()
            }
        }

        // 设置整个pipeline 的超时时间为 1个小时

        options {
            timeout(time:1, unit: 'HOURS')
        }

        // 添加环境变量
        environment {
            TEST_W = 'RMB'
        }

        stages {

            stage('Compile') {
                // 只有 master分支进行的步骤
                when {
                    allOf {
                        branch 'master'
                    }
                }

                steps {
                    container('compile') {
                        sh '''
                    cp -rp `pwd` /go/src/node_exporter
                    currentPath=`pwd`
                    cd /go/src/node_exporter
                    CGO_ENABLED=0 go build -o ${currentPath}/dmai_node_exporter
                    cd -
                    '''
                        withCredentials([usernamePassword(credentialsId: 'devops-use', passwordVariable: 'password', usernameVariable: 'username')]) {
                            sh 'git clone https://$username:$password@gitlab.dm-ai.cn/application-engineering/devops/ansible.git'
                        }
                        sh 'cp -rp dmai_node_exporter ansible/roles/prometheus.node_exporter/files/dmai_node_exporter'
                    }
                }
            }

            stage('Install node exporter') {
                steps {
                    container('ansible') {
                        sh 'cd ansible; ansible-playbook common.yml -t prometheus-node-exporter'
                    }
                }
            }

        }

        post {
            always {
                echo "over!!"
            }

            failure {
                echo "fail"
                script {
                    def jobName = env.JOB_NAME.split("/")[0]
                    echo jobName
                    emailext (
                            body: email.faildBody(jobName),
                            subject: 'Jenkins build faild info',
                            to: 'liaolonglong@dm-ai.cn'
                    )
                }
            }

            success {
                script {
                    def jobName = env.JOB_NAME.split("/")[0]
                    echo jobName
                    emailext (
                            body: email.showEnv(env, 'success'),
                            subject: 'Jenkins build success info',
                            to: 'liaolonglong@dm-ai.cn'
                    )
                }
            }
        }
    }
}

def baseTemplateName() {
    return 'base-template'
}
def compileNodeExporterTemplate() {
    return """
apiVersion: v1
kind: Pod
metadata:
  name: jenkins-build-node-exporter
  namespace: devops
spec:
  imagePullSecrets:
  - name: regsecret
  containers:
  - name: ansible
    image: docker.dm-ai.cn/devops/base-image-exec-ansible:0.02
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn 
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
  # 编译  node-exporter
  - name: compile
    image: docker.dm-ai.cn/devops/base-image-docker-compile-golang:0.01
    imagePullPolicy: IfNotPresent
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
  nodeSelector:
    makeenv: cpu
"""
}