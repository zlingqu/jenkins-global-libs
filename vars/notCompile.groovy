
import java.io.*

def call(Map map, env) {

    // 临时的，后面再进行抽取
    def globalConfig = [
            'frontend-test' : [
                    'nodePort': '31377'
            ],
            'service-prometheus' : [
                    'nodePort': '30090',
                    'namespace': 'devops',
                    'containerPort': '9090'
            ],
            'prometheus-alertmanager': [
                    'namespace': 'devops',
                    'containerPort': '9093'
            ]
    ]

    map.put('globalConfig', globalConfig)

    println('【开始进行构建】')
    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-test'
                label 'jenkinsTemplate'
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
            dockerFile = dockerFileContent(map)
            dockerComposeFile = dockerComposeFile(map)
            kubernetesContentDeployFile = kubernetesContent(map)
        }

        stages {
            stage('Make image') {
                steps {
                    container('docker-compose') {
                        sh 'sleep 6000'
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
  - name: kubectl 
    image: docker.dm-ai.cn/devops/base-image-kubectl:0.01
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
    - "/bin/sh"
    - "-c"
    args:
    - "cat"
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
"""
}


def dockerFileContent(map) {
//    return '''
//FROM nginx
//ENV TZ=Asia/Shanghai
//ADD dist /usr/share/nginx/html
//ADD nginx.conf /etc/nginx/conf.d/default.conf
//RUN ln -sf /dev/stdout /var/log/nginx/access.log
//RUN ln -sf /dev/stderr /var/log/nginx/error.log
//EXPOSE 80
//ENTRYPOINT nginx -g "daemon off;"
//'''
    switch (map.get('appName')) {
        case 'service-prometheus':
            return '''
FROM centos:latest
WORKDIR /workspace
RUN mkdir -p /data/prometheus/etc/jobs && mkdir -p /data/prometheus/etc/rules/
COPY ./prometheus-2.9.2.linux-amd64.tar.gz /workspace/
COPY ./prometheus.yml /workspace/prometheus.yml
COPY ./alert_rule.yml /data/prometheus/etc/rules/alert_rule.yml
COPY ./test_server.yml /data/prometheus/etc/jobs/test_server.yml
COPY ./conf/ceph.yml /data/prometheus/etc/jobs/ceph.yml
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
RUN tar xf alertmanager-0.17.0.linux-amd64.tar.gz && mv alertmanager-0.17.0.linux-amd64 alertmanager
'''
    }
//
}

def dockerComposeFile(map) {
    def text = '''
version: "2"
services:
  service-docker-build:
    build: ./
    image: $dockerRegistryHost/$imageUrlPath:$imageTags
'''
    def binding = [
            'imageUrlPath' : map.imageUrlPath,
            'imageTags' : map.imageTags,
            'dockerRegistryHost' : map.dockerRegistryHost,
    ]

    return simpleTemplate(text, binding)
}

def kubernetesContent(map) {
    if (map.appName == "prometheus-alertmanager") {
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
            memory: 200Mi
          requests:
            cpu: 100m
            memory: 200Mi
        ports:
        - containerPort: $containerPort
'''
        def binding = [
                'imageUrlPath' : map.imageUrlPath,
                'imageTags' : map.imageTags,
                'dockerRegistryHost' : map.dockerRegistryHost,
                'appName' : map.appName,
                'namespace' : getGlobal(map, 'namespace'),
                'containerPort': getGlobal(map, 'containerPort')
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
            cpu: 500m
            memory: 1000Mi
          requests:
            cpu: 100m
            memory: 200Mi
        ports:
        - containerPort: $containerPort
      volumes:
      - name: cephfs
        persistentVolumeClaim:
          claimName: mypvc
'''
    def binding = [
            'imageUrlPath' : map.imageUrlPath,
            'imageTags' : map.imageTags,
            'dockerRegistryHost' : map.dockerRegistryHost,
            'appName' : map.appName,
            'nodePort' : getGlobal(map, 'nodePort'),
            'namespace' : getGlobal(map, 'namespace'),
            'containerPort': getGlobal(map, 'containerPort')
    ]

    return simpleTemplate(text, binding)
}

def getGlobal(map, getKey) {
    return map.get('globalConfig').get(map.appName).get(getKey)
}

def emailBody(env, buildResult, Map map) {
    def text = '''Job build $buildResult Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline
App url addRess :  $appurl
'''
    def binding = [
            'jobName' :  env.JOB_NAME.split("/")[0],
            'branchName' : env.BRANCH_NAME,
            'buildNumber' : env.BUILD_NUMBER,
            'buildResult': buildResult,
            'appurl' : 'http://192.168.3.140:' +  map.get('globalConfig').get(map.appName).get('nodePort')
    ]
    return simpleTemplate(text, binding)
}

def simpleTemplate(text, binding) {
    def engine = new groovy.text.SimpleTemplateEngine()
    def template = engine.createTemplate(text).make(binding)
    return template.toString()
}
