import com.dmai.Conf
import com.dmai.JenkinsRunTemplate
import com.dmai.MakeDockerImage
import com.dmai.Deploykubernetes
import com.tool.Tools

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
                        deploykubernetes.deployKubernetes()
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

static def emailBody(Conf conf, String buildResult) {
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
    return Tools.simpleTemplate(text, binding)
}

//def simpleTemplate(text, binding) {
//    def engine = new groovy.text.SimpleTemplateEngine()
//    def template = engine.createTemplate(text).make(binding)
//    return template.toString()
//}

//def map = [:]
//map.put('appName','service-prometheus')
//call(map, [:])