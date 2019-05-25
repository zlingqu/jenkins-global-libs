import java.io.*

def call(Map map, env) {

    println('开始进行构建！')
    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-test'
                label 'yarnTemplate'
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom baseTemplateName()
                yaml yarnTemplate()
            }
        }

        options {
            timeout(time:1, unit: 'HOURS')
            retry(2)
        }

        environment {
            tags = "${map.REPO_URL}"
            dockerFile = dockerFileContent()
            dockerComposeFile = dockerComposeFile()
            kubernetesContentDeployFile = kubernetesContent()
        }

        stages {
            stage('Compile') {
                steps {
                    container('compile') {
                        println("【开始进行编译】")
                        sh '''
                            npm config set registry=http://192.168.3.13:8081/repository/npm/
                           '''
                    }
                }
            }

            stage('Make image') {
                steps {
                    container('docker-compose') {
                        println('【创建Dockerfile】')
                        sh 'echo "${dockerFile}" > Dockerfile'

                        println('【创建docker-compose】')
                        sh 'echo -e "${dockerComposeFile}" > docker-compose.yml'

                        println('【Make image】')
                        sh 'sleep 60000'
                        sh 'docker-compose build'

                        println('【Push image】')
//                    sh 'docker-compose push'
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
                            body: emailBody(env, 'success'),
                            subject: 'Jenkins build faild info',
                            to: "${map.emailAddress}"
                    )
                }
            }

            success {
                script {
                    emailext (
                            body: emailBody(env, 'success'),
                            subject: 'Jenkins build success info',
                            to: "${map.emailAddress}"
                    )
                }
            }
        }

    }
    pipeline {
//        agent {
//            kubernetes {
//                cloud 'kubernetes-test'
//                label 'yarnTemplate'
//                defaultContainer 'jnlp'
//                namespace 'devops'
//                inheritFrom baseTemplateName()
//                yaml yarnTemplate()
//            }
//        }

        // 设置整个pipeline 的超时时间为 1个小时

//        options {
//            timeout(time:1, unit: 'HOURS')
//            retry(2)
//        }

        // 添加环境变量
//        environment {
//            tags = "${map.REPO_URL}"
//            dockerFile = dockerFileContent()
//            dockerComposeFile = dockerComposeFile()
//            kubernetesContentDeployFile = kubernetesContent()
//        }

//        stages {
//            stage('Compile') {
//                steps {
//                    container('compile') {
//                        println("【开始进行编译】")
//                    }
//                    sh '''
//                       npm config set registry=http://192.168.3.13:8081/repository/npm/
//                       npm install
//                       npm run build
//                       '''
//                }
//            }
//
//            stage('Make image') {
//                container('docker-compose') {
//                    println('【创建Dockerfile】')
//                    sh 'echo $dockerFile > Dockerfile'
//
//                    println('【创建docker-compose】')
//                    sh 'echo $dockerComposeFile > docker-compose.yml'
//
//                    println('【Make image】')
//                    sh 'docker-compose build'
//
//                    println('【Push image】')
////                    sh 'docker-compose push'
//                }
//            }
//
//
//        }

//        post {
//            always {
//                echo "over!!"
//            }
//
//            failure {
//                script {
//                    emailext (
//                            body: emailBody(env, 'success'),
//                            subject: 'Jenkins build faild info',
//                            to: 'zuosheng@dm-ai.cn'
//                    )
//                }
//            }
//
//            success {
//                script {
//                    emailext (
//                            body: emailBody(env, 'success'),
//                            subject: 'Jenkins build success info',
//                            to: 'zuosheng@dm-ai.cn'
//                    )
//                }
//            }
//        }

//        stages {
//            stage('Compile') {
//                steps {
//                    container('compile') {
//                        sh '''
//                    chmod -R 777 `pwd`
//                    npm config set registry=http://192.168.3.13:8081/repository/npm/
//                    npm install
//                    npm config set registry=http://192.168.3.13:8081/repository/npm/
//                    npm run build
//                    '''
//                    }
//                }
//            }
//
//            stage('Make image') {
//                steps {
//                    container('docker-compose') {
//                        sh 'docker-compose build'
//                        sh 'docker-compose push'
//                    }
//                }
//            }
//
//            stage('Deploy') {
//                steps {
//                    kubernetesDeploy configs: 'Deploy-k8s.yml', kubeConfig: [path: ''], kubeconfigId: 'k8s-deploy-test', secretName: '', ssh: [sshCredentialsId: '*', sshServer: ''], textCredentials: [certificateAuthorityData: '', clientCertificateData: '', clientKeyData: '', serverUrl: 'https://']
//
//                }
//            }
//
//        }

//        post {
//            always {
//                echo "over!!"
//            }
//
//            failure {
//                echo "fail"
//                script {
//                    def jobName = env.JOB_NAME.split("/")[0]
//                    echo jobName
//                    emailext (
//                            body: faildBody(jobName),
//                            subject: 'Jenkins build faild info',
//                            to: 'zuosheng@dm-ai.cn'
//                    )
//                }
//            }
//
//            success {
//                script {
//                    def jobName = env.JOB_NAME.split("/")[0]
//                    echo jobName
//                    emailext (
//                            body: emailBody(env, 'success'),
//                            subject: 'Jenkins build success info',
//                            to: 'qinyadong@dm-ai.cn'
//                    )
//                }
//            }
//        }
    }
}

def baseTemplateName() {
    return 'base-template'
}

def yarnTemplate() {
    return """
apiVersion: v1
kind: Pod
metadata:
  name: yarnTemplate
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
        memory: 1000Mi
        cpu: 800m
      requests:
        cpu: 400m
        memory: 600Mi
  - name: compile
    image: docker.dm-ai.cn/devops/base-image-compile-frontend:0.03
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
        memory: 8000Mi
        cpu: 5500m
      requests:
        cpu: 4500m
        memory: 7000Mi
  volumes:
  - name: sock
    hostPath:
      path: /var/run/docker.sock      
"""
}


def dockerFileContent() {
    return '''
FROM nginx
LABEL maintainer="qinyadong"
ENV TZ=Asia/Shanghai
ADD dist /usr/share/nginx/html
ADD nginx.conf /etc/nginx/conf.d/default.conf
RUN ln -sf /dev/stdout /var/log/nginx/access.log
RUN ln -sf /dev/stderr /var/log/nginx/error.log
EXPOSE 80
ENTRYPOINT nginx -g "daemon off;"
'''
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
            'imageUrlPath' : '11111',
            'imageTags' : '22222',
            'dockerRegistryHost' : '3333',
    ]

    return simpleTemplate(text, binding)
}

def kubernetesContent() {
    return '''
---
apiVersion: v1
kind: Service
metadata:
  labels:
    app: work-attendance-frontend
  name: work-attendance-frontend
  namespace: mis
spec:
  ports:
  - port: 80
    protocol: TCP
    targetPort: 80
    nodePort: 30800
  selector:
    app: work-attendance-frontend
  type: NodePort

---
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: work-attendance-frontend
  namespace: mis
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: work-attendance-frontend
    spec:
      imagePullSecrets:
      - name: regsecret
      containers:
      - name: service-prometheus
        image: docker.dm-ai.cn/mis/work-attendance-frontend:0.0.1
        imagePullPolicy: Always #
        env: #指定容器中的环境变量
        - name: TZ
          value: Asia/Shanghai
        resources:
          limits:
            memory: 500Mi
          requests:
            cpu: 100m
            memory: 200Mi
        ports:
        - containerPort: 80
'''

}

def createDockerFile(fileName) {

    file = new File(fileName)
    println(1111111)
    println(fileName)
    println(file)

    if (file.exists()) {
        println("文件存在，删除文件！！")
        file.delete()
    } else {
        println("文件不存在！！！")
        file.createNewFile()
    }

    def printWriter = file.newPrintWriter()
    printWriter.write(dockerFileContent())
    printWriter.flush()
    printWriter.close()

//    File file = new File(fileName);
//    FileOutputStream out = null;
//    println("wocao11")
//    try {
//        System.out.println(fileName);
//        if (file.exists()) {
//            file.delete()
//        }
//
//        if (!file.exists()) {
//            // 先得到文件的上级目录，并创建上级目录，在创建文件
//            file.getParentFile().mkdir();
//            file.createNewFile();
//        }
//
//        //创建文件输出流
//        out = new FileOutputStream(file);
//        //将字符串转化为字节
//        byte[] byteArr = "FileInputStream Test".getBytes();
//        out.write(byteArr);
//        out.close();
//    } catch (FileNotFoundException e) {
//        e.printStackTrace();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }

//    println("woshitest")
//
//    File file = new File(fileName)
//    FileOutputStream out = null;
//
//    println(file.getCanonicalPath())
//
//    if(!file.exists()){
//        //先得到文件的上级目录，并创建上级目录，在创建文件
//        file.getParentFile().mkdir();
//        try {
//            //创建文件
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    println(22222222222)
//    if (file.exists())
//        file.delete()
//    println(1111111111)
//    def printWriter = file.newPrintWriter()
//    printWriter.write(dockerFileContent())
//    printWriter.flush()
//    printWriter.close()
}


def faildBody(jobName) {
    return """Job build faild. Address : Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/""" + jobName + """/detail/${env.BRANCH_NAME}/${env.BUILD_NUMBER}/pipeline"""
}

def emailBody(env, buildResult) {
    def text = 'Job build $buildResult Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline'
    def binding = [
            'jobName' :  env.JOB_NAME.split("/")[0],
            'branchName' : env.BRANCH_NAME,
            'buildNumber' : env.BUILD_NUMBER,
            'buildResult': buildResult,
    ]
    def engine = new groovy.text.SimpleTemplateEngine()
    def template = engine.createTemplate(text).make(binding)
    return template.toString()
}

def simpleTemplate(text, binding) {
    def engine = new groovy.text.SimpleTemplateEngine()
    def template = engine.createTemplate(text).make(binding)
    return template.toString()
}
//new File(fileName).withPrintWriter { printWriter ->
//    printWriter.println('The first content of file')
//}
