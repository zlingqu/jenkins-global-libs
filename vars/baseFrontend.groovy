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

        // 设置整个pipeline 的超时时间为 1个小时

        options {
            timeout(time:1, unit: 'HOURS')
            retry(2)
        }

        // 添加环境变量
        environment {
            tags = "${map.REPO_URL}"
        }

        stages {
            stage('Compile') {
                steps {
                    container('yarn-compile') {
                        sh 'hostname'
                        sh 'pwd && chmod -R 777 `pwd`'
                        sh 'sleep 60000'
                        createDockerFile('/home/jenkins/workspace/' + env.JOB_NAME.split("/")[0] + '_' + env.BRANCH_NAME + '/Dockerfile')
                        echo '11111'
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
                            body: showEnv(env, 'success'),
                            subject: 'Jenkins build faild info',
                            to: 'zuosheng@dm-ai.cn'
                    )
                }
            }

            success {
                script {
                    emailext (
                            body: showEnv(env, 'success'),
                            subject: 'Jenkins build success info',
                            to: 'zuosheng@dm-ai.cn'
                    )
                }
            }
        }

//        stages {
//            stage('Compile') {
//                steps {
//                    container('yarn-compile') {
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
//                            body: showEnv(env, 'success'),
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
  - name: yarn-compile
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

def showEnv(env, buildResult) {
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
//new File(fileName).withPrintWriter { printWriter ->
//    printWriter.println('The first content of file')
//}
