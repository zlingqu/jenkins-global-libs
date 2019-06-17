import com.dmai.Conf

def call(Map map, env) {

    // 定义定义的全局的配置项目
    String appName = map.get('appName')
    Conf conf = new Conf(this, appName, map)

    // 把用户设置的全局的属性，加入到默认的全局的设置当中
    conf.setUserAttr(map)

    // 注入jenkins的环境变量到全局的Conf
    conf.setJenkinsAttrToConf(env)

    println('【开始进行构建】')
    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-dev'
                label appName
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom 'base-template'
                yaml jenkinsTemplate(conf)
            }
        }

        options {
            timeout(time:1, unit: 'HOURS')
//            retry(2)
        }

        environment {
            dockerFile = dockerFileContent(conf)
            dockerComposeFile = dockerComposeFile(conf)
            kubernetesContentDeployFile = kubernetesContent(conf)
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

            stage('Deploy') {
                steps {
                    container('kubectl') {
                        println('【创建k8s部署文件】')
                        sh 'echo "${kubernetesContentDeployFile}" > Deploy-k8s.yml'
                        println('【执行部署】')
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
                            body: emailBody(conf, 'success'),
                            subject: 'Jenkins build faild info',
                            to: conf.getAttr('emailAddress')

                    )
                }
            }

            success {
                script {
                    emailext (
                            body: emailBody(conf, 'success'),
                            subject: 'Jenkins build success info',
                            to: conf.getAttr('emailAddress')
                    )
                }
            }
        }

    }
}

def jenkinsTemplate(Conf conf) {
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
  - name: kubectl 
    image: docker.dm-ai.cn/$kubectlImage
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
        memory: 300Mi
        cpu: 200m
      requests:
        cpu: 100m
        memory: 200Mi  
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
        memory: 1000Mi
        cpu: 800m
      requests:
        cpu: 400m
        memory: 600Mi
  volumes:
  - name: sock
    hostPath:
      path: /var/run/docker.sock
'''
    def binding = [
            'kubectlImage' : conf.getAttr('kubectlImage'),
    ]

    return simpleTemplate(text, binding)
}


def dockerFileContent(Conf conf) {
    switch (conf.appName) {
        case 'service-prometheus':
            return '''
FROM centos:latest
WORKDIR /workspace
RUN mkdir -p /data/prometheus/etc/jobs && mkdir -p /data/prometheus/etc/rules/
COPY ./prometheus-2.9.2.linux-amd64.tar.gz /workspace/
COPY ./prometheus.yml /workspace/prometheus.yml
COPY ./alert_rule.yml /data/prometheus/etc/rules/alert_rule.yml
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN tar xf prometheus-2.9.2.linux-amd64.tar.gz && mv prometheus-2.9.2.linux-amd64 prometheus
'''
        case 'prometheus-alertmanager':
            return '''
FROM centos:latest
WORKDIR /workspace
RUN mkdir -p /data/prometheus/alertmanager
COPY ./alertmanager.yml /data/prometheus/alertmanager/alertmanager.yml
COPY ./template /data/prometheus/alertmanager/template
COPY ./alertmanager-0.17.0.linux-amd64.tar.gz /workspace/
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN tar xf alertmanager-0.17.0.linux-amd64.tar.gz && mv alertmanager-0.17.0.linux-amd64 alertmanager
'''
        case 'blackbox-exporter':
            return '''
FROM golang:1.12.5-alpine3.9
ADD . /go/blackbox_exporter
'''
    }
//
}

def dockerComposeFile(Conf conf) {
    def text = '''
version: "2"
services:
  service-docker-build:
    build: ./
    image: $dockerRegistryHost/$imageUrlPath:$imageTags
'''
    def binding = [
            'imageUrlPath' : conf.getAttr('imageUrlPath'),
            'imageTags' : conf.getAttr('imageTags'),
            'dockerRegistryHost' : conf.getAttr('dockerRegistryHost'),
    ]

    return simpleTemplate(text, binding)
}

def kubernetesContent(Conf conf) {
    if (conf.appName == "blackbox-exporter") {
        def text = '''
apiVersion: v1
kind: Service
metadata:
  labels:
    app: $appName
  name: $appName
  namespace: $namespace
spec:
  ports:
  - port: $containerPort
    protocol: TCP
    targetPort: $containerPort
    nodePort: $nodePort
  selector:
    app: $appName
  type: NodePort

---
apiVersion: extensions/v1beta1
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
      - name: service-prometheus
        image: $dockerRegistryHost/$imageUrlPath:$imageTags
        imagePullPolicy: Always #
        command:
        - "/go/blackbox_exporter/blackbox_exporter-0.14.0.linux-amd64/blackbox_exporter"
        args:
        - "--config.file=/go/blackbox_exporter/blackbox_exporter-0.14.0.linux-amd64/blackbox.yml"
        resources:
          limits:
            memory: 400Mi
            cpu: 200m
          requests:
            cpu: 100m
            memory: 200Mi
        ports:
        - containerPort: $containerPort
'''
        def binding = [
                'imageUrlPath' : conf.getAttr('imageUrlPath'),
                'imageTags' : conf.getAttr('imageTags'),
                'dockerRegistryHost' : conf.getAttr('dockerRegistryHost'),
                'appName' : conf.appName,
                'namespace' : conf.getAttr('namespace'),
                'containerPort': conf.getAttr('containerPort'),
                'nodePort' : conf.getAttr('nodePort'),
        ]

        return simpleTemplate(text, binding)
    }

    if (conf.appName == "prometheus-alertmanager") {
        def text =  '''
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
  - port: $containerPort
    protocol: TCP
    targetPort: $containerPort
  selector:
    app: $appName
  type: ClusterIP

---
apiVersion: extensions/v1beta1
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
      - name: service-prometheus
        image: $dockerRegistryHost/$imageUrlPath:$imageTags
        imagePullPolicy: Always #
        command:
        - "/workspace/alertmanager/alertmanager"
        args:
        - "--config.file=/data/prometheus/alertmanager/alertmanager.yml"
        resources:
          limits:
            memory: 1500Mi
            cpu: 1000m
          requests:
            cpu: 500m
            memory: 1000Mi
        ports:
        - containerPort: $containerPort
'''
        def binding = [
                'imageUrlPath' : conf.getAttr('imageUrlPath'),
                'imageTags' : conf.getAttr('imageTags'),
                'dockerRegistryHost' : conf.getAttr('dockerRegistryHost'),
                'appName' : conf.appName,
                'namespace' : conf.getAttr('namespace'),
                'containerPort': conf.getAttr('containerPort')
        ]

        return simpleTemplate(text, binding)
    }

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
  - port: $containerPort
    protocol: TCP
    targetPort: $containerPort
    nodePort: $nodePort
  selector:
    app: $appName
  type: NodePort

---
apiVersion: extensions/v1beta1
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
        image: $dockerRegistryHost/$imageUrlPath:$imageTags
        imagePullPolicy: Always #
        volumeMounts:
        - name: cephfs
          mountPath: /data/tsdb
          subPath: prometheus_home/prometheus_server_data_home/tsdb
        command:
        - "/workspace/prometheus/prometheus"
        args:
        - "--config.file=/workspace/prometheus.yml"
        - "--storage.tsdb.path=/data/tsdb"
        resources:
          limits:
            cpu: 3500m
            memory: 5000Mi
          requests:
            cpu: 2000m
            memory: 4000Mi
        ports:
        - containerPort: $containerPort
      volumes:
      - name: cephfs
        persistentVolumeClaim:
          claimName: mypvc
      nodeSelector:
        makeenv: prometheus
'''
    def binding = [
            'imageUrlPath' : conf.getAttr('imageUrlPath'),
            'imageTags' : conf.getAttr('imageTags'),
            'dockerRegistryHost' : conf.getAttr('dockerRegistryHost'),
            'appName' : conf.appName,
            'nodePort' : conf.getAttr('nodePort'),
            'namespace' : conf.getAttr('namespace'),
            'containerPort': conf.getAttr('containerPort')
    ]

    return simpleTemplate(text, binding)
}

def emailBody(Conf conf, String buildResult) {
    def text = '''Job build $buildResult Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline
App url addRess :  $appurl
'''
    def binding = [
            'jobName' :  conf.getAttr('jobName'),
            'branchName' : conf.getAttr('branchName'),
            'buildNumber' : conf.getAttr('buildNumber'),
            'buildResult': buildResult,
            'appurl' : conf.getAttr('domain')
    ]
    return simpleTemplate(text, binding)
}

def simpleTemplate(text, binding) {
    def engine = new groovy.text.SimpleTemplateEngine()
    def template = engine.createTemplate(text).make(binding)
    return template.toString()
}

//def map = [:]
//map.put('appName','service-prometheus')
//call(map, [:])