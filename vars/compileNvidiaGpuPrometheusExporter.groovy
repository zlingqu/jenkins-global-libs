
def call(Map map, env) {

    pipeline {
        agent none

        options {
            timeout(time:1, unit: 'HOURS')
            retry(2)
        }

        environment {
            gpuExporterCompilePath = "/root/go/src/github.com/mindprince/nvidia_gpu_prometheus_exporter"
        }
        stages {
            agent { label 'compile_exporter' }
            stage('Compile') {
                sh '''
                   yum install -y go
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