
def call(Map map, env) {

    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-test'
                label 'nodeTemplate'
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
            gpuExporterCompilePath = "/go/src/github.com/mindprince/nvidia_gpu_prometheus_exporter"
        }
        stages {
                stage('Compile') {
                    steps {
                        container('compile') {
                            sh '''
                           echo ${gpuExporterCompilePath}
                           mkdir -p ${gpuExporterCompilePath}
                           cp -rp . ${gpuExporterCompilePath}
                           currentPath=pwd
                           cd ${gpuExporterCompilePath} && go build
                           go build -o ${currentPath}/dmai_gpu_exporter
                        '''
                        }
                    }
                }
        }
    }
}


def baseTemplateName() {
    return 'base-template'
}

jenkinsTemplate(env) {
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
        memory: 5000Mi
        cpu: 3000m
      requests:
        cpu: 2000m
        memory: 4000Mi
  - name: compile
    image: golang:1.10
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
        cpu: 1000m
        memory: 2000Mi          
"""
}