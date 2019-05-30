def call(Map map, env) {
    println("this is a test")

    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-test'
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
                    cd /go/src/node_exporter
                    currentPath=`pwd`
                    CGO_ENABLED=0 go build -o ${currentPath}/dmai_node_exporter
                    cd -
                    '''
                        withCredentials([usernamePassword(credentialsId: 'passwd-zs', passwordVariable: 'password', usernameVariable: 'username')]) {
                            sh 'git clone http://$username:$password@192.168.3.221/application-engineering/devops/ansible.git'
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
                            to: 'zuosheng@dm-ai.cn'
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
                            to: 'zuosheng@dm-ai.cn'
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
    - "/bin/sh"
    - "-c"
    args:
    - "cat"
    tty: true
    resources:
      limits:
        memory: 2000Mi
        cpu: 1500m
      requests:
        cpu: 1000m
        memory: 1500Mi  
  # 编译  node-exporter
  - name: compile
    image: docker.dm-ai.cn/devops/base-image-docker-compile-golang:0.01
    imagePullPolicy: IfNotPresent
    command:
    - "/bin/sh"
    - "-c"
    args:
    - "cat"
    tty: true
    resources:
      limits:
        memory: 400Mi
      requests:
        cpu: 100m
        memory: 200Mi
  nodeSelector:
    makeenv: jenkins         
"""
}

def notComplieTemplate() {
    return """
apiVersion: v1
kind: Pod
metadata:
  name: notComplieTemplate
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
    - "/bin/sh"
    - "-c"
    args:
    - "cat"
    tty: true
    resources:
      limits:
        memory: 500Mi
        cpu: 300m
      requests:
        cpu: 100m
        memory: 200Mi
  volumes:
  - name: sock
    hostPath:
      path: /var/run/docker.sock      
"""
}
//call()