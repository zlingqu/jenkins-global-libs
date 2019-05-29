
def call(Map map, env) {

    pipeline {
        agent none

        stages {
            agent { label 'compile_exporter' }
            stage('Compile') {
                sh '''
                   yum install -y go
                   go get github.com/mindprince/nvidia_gpu_prometheus_exporter
                   cd /root/go/src/github.com/mindprince/nvidia_gpu_prometheus_exporter
                   go build
                   '''
            }
        }
    }
}