def call() {
    println("this is a test")
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
  # 编译  node-exporter
  - name: compile-node-exporter
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
    volumeMounts:
    - name: prometheus-node-exporter-home
      mountPath: /data
      subPath: prometheus_home/node_exporter
  volumes:
  - name: prometheus-node-exporter-home
    persistentVolumeClaim:
      claimName: mypvc
"""
}

//call()