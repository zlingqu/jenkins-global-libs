//import com.dmai.Conf
//import com.dmai.JenkinsRunTemplate
//import com.dmai.MakeDockerImage
//import com.dmai.Deploykubernetes
//import com.dmai.DmaiEmail

import com.dmai.*

def call(Map map, env) {

    // 定义定义的全局的配置项目
    String appName = map.get('appName')
    Conf conf = new Conf(appName, map)

    // 把用户设置的全局的属性，加入到默认的全局的设置当中
    conf.setUserAttr(map)

    // 注入jenkins的环境变量到全局的Conf
    conf.setJenkinsAttrToConf(env)

    // 全局 docker 镜像生成
    MakeDockerImage makeDockerImage = new MakeDockerImage(this, conf)

    // 自动生成的k8s，部署文件
    Deploykubernetes deploykubernetes = new Deploykubernetes(this, conf)

    // 初始化邮件发送模块
    DmaiEmail dmaiEmail = new DmaiEmail(this, conf)

    println('【开始进行构建】')
    pipeline {
        agent {
            kubernetes {
                cloud 'kubernetes-dev'
                label appName
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom 'base-template'
                yaml new JenkinsRunTemplate(conf).getJenkinsRunTemplate()
            }
        }

        options {
            timeout(time:1, unit: 'HOURS')
        }

//        environment {
//            kubernetesContentDeployFile = kubernetesContent(conf)
//        }

        stages {
            stage('Make Image') {
                steps {
                    container('docker-compose') {
                        script {
                            makeDockerImage.makeImage()
                        }
                    }
                }
            }

            stage('Push Image') {
                steps {
                    container('docker-compose') {
                        script {
                            makeDockerImage.pushImage()
                        }
                    }
                }
            }

            stage('Deploy') {
                steps {
                    container('kubectl') {
                        script {
                            deploykubernetes.deployKubernetes()
                        }
                    }
                }
            }
        }

        post {
            always {
                script {
                    dmaiEmail.sendEmail('构建完成！')
                }
            }

            failure {
                script {
                    dmaiEmail.sendEmail('构建失败！')
                }
            }

            success {
                script {
                    dmaiEmail.sendEmail('构建成功！')
                }
            }
        }

    }
}