import com.dmai.*

def call(Map map, env) {
    // 默认master 和 dev分支才进行构建
    if (! (env.BRANCH_NAME in ['master', 'dev'])) return

    // 定义定义的全局的配置项目
    String appName = map.get('appName')
    Conf conf = new Conf(this, appName, map)

    // 把用户设置的全局的属性，加入到默认的全局的设置当中
    conf.setUserAttr(map)

    // 注入jenkins的环境变量到全局的Conf
    conf.setJenkinsAttrToConf(env)

    // 初始化编译模块
    Compile compile = new Compile(this, conf)

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

        // 设置任务的超时时间为1个小时。
        options {
            timeout(time:1, unit: 'HOURS')
            retry(2)
        }

        stages {
            stage('Compile') {

                // 当项目的全局选项设置为compile == true的时候，才进行部署的操作
                when { expression { return conf.getAttr('compile') } }
                steps {
                    container('compile') {
                        script {
                            compile.compile()
                        }
                    }
                }
            }

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

            stage('Download Config file') {
                when { expression { return conf.getAttr('deploy') } }

                steps {
                    container('kubectl') {
                        script {
                            withCredentials([usernamePassword(credentialsId: 'devops-use', passwordVariable: 'password', usernameVariable: 'username')]) {
                                sh 'git clone https://$username:$password@gitlab.dm-ai.cn/application-engineering/devops/deployment.git'
                            }
                        }
                    }
                }
            }

            stage('Deploy') {

                // 当项目的全局选项设置为deploy == true的时候，才进行部署的操作
                when { expression { return conf.getAttr('deploy') } }

                steps {
                    container('kubectl') {
                        script {
                            deploykubernetes.deployKubernetes()
                        }
                    }
                }
            }

            stage('Deploy stage') {
                when { expression { return conf.getAttr('stage') } }

                steps {
                    container('kubectl-stage') {
                        script {
                            deploykubernetes.deployKubernetes()
                        }
                    }
                }
            }
        }

        post {
            always {
                echo "构建完成！"
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