import com.dmai.*

def call(Map map, env) {
    // 默认master 和 dev分支才进行构建
    if (!(env.BRANCH_NAME in ['master', 'dev'])) return

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

    // 初始化code check 的步骤
    CodeCheck codeCheck = new CodeCheck(this, conf)

    // 初始化邮件发送模块
    DmaiEmail dmaiEmail = new DmaiEmail(this, conf)

    //
    if (conf.getAttr('branchName') == 'master' && conf.getAttr('master') !='prd') return

    println('【开始进行构建】')
    pipeline {

        // 在整个构建之前，先进行参数化的设置
        parameters {
            choice(name: 'DEPLOY_ENV', choices: ['dev', 'test'], description: 'dev分支部署的环境，目前支持：dev/test。')
            string(name: 'GIT_VERSION', defaultValue: 'last', description: 'git的commit 版本号，git log 查看。')
            string(name: 'VUE_APP_SCHOOL', defaultValue: 's00001', description: '学校的Code，xmc2-frontend项目使用，其他不关注,s小写')
            choice(name: 'VUE_APP_SCENE', choices: ['main', 'training'], description: 'xmc2-frontend项目使用，其他不关注')
        }

//        triggers {
//            cron('H/30 * * * *')
//        }

        // 转化为可用的环境变量
        environment {
            deployEnvironment = "${params.DEPLOY_ENV}"
            gitVersion = "${params.GIT_VERSION}"
            vueAppScene = "${params.VUE_APP_SCENE}"
            vueAppSchool = "${params.VUE_APP_SCHOOL}"
        }

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
            timeout(time: 1, unit: 'HOURS')
            retry(2)
        }

        stages {

            stage('Build-Init') {
                steps {
                    script {
                        if (conf.appName == 'xmc2-frontend') {
                            conf.setAppName(conf.appName + (vueAppScene == 'main' ? '' : '-' + vueAppScene) + (vueAppSchool == 'S00001' ? '' : '-' + vueAppSchool)  )
                            conf.setVueAppSchool(vueAppSchool)
                            echo conf.vueAppScene
                            echo conf.vueAppSchool
                        }
                    }
                }
            }

            stage('Specified version') {
                when { expression { return  gitVersion != 'last'} }
                steps {
                    container('kubectl') {
                        script {
                            try {
                                withCredentials([usernamePassword(credentialsId: 'passwd-zs', passwordVariable: 'password', usernameVariable: 'username')]) {
                                    sh 'source /etc/profile; git config --global http.sslVerify false ; git reset --hard "${gitVersion}"'
                                }
                            } catch (e) {
                                sh "echo ${e}"
                            }
                        }
                    }
                }
            }

            stage('Exec Command') {
                when { expression { return  conf.getAttr('useCustomImage')} }
                steps {
                    container('custom-image') {
                        sh conf.getAttr('execCommand')
                    }
                }
            }

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

            stage('Download Config file') {
                when { expression { return conf.getAttr('deploy') } }

                steps {
                    container('kubectl') {
                        script {
                            try {
                                withCredentials([usernamePassword(credentialsId: 'passwd-zs', passwordVariable: 'password', usernameVariable: 'username')]) {
                                    sh 'source /etc/profile; git config --global http.sslVerify false ; git clone https://$username:$password@gitlab.dm-ai.cn/application-engineering/devops/deployment.git'
                                }
                            } catch (e) {
                                sh "echo ${e}"
                            }
                        }
                    }
                }
            }

            stage('Make Image') {
                when { expression { return  conf.getAttr('makeImage')} }
                steps {
                    container('docker-compose') {
                        script {
                            makeDockerImage.makeImage()
                        }
                    }
                }
            }

            stage('Push Image') {
                when { expression { return  conf.getAttr('makeImage')} }
                steps {
                    container('docker-compose') {
                        script {
                            makeDockerImage.pushImage()
                        }
                    }
                }
            }

            stage('Install istanbul') {
                when { expression { return  conf.getAttr('codeLanguage') in  ['js', 'node'] && conf.getAttr('sonarCheck') && deployEnvironment != 'test'}  }
                steps {
                    container('compile') {
                        script {
                            sh 'npm config set registry http://192.168.3.13:8081/repository/npm && npm install || echo 0'
                            sh 'npm install -g istanbul || echo 0'
                            sh 'istanbul cover test/*.js --my test args || echo 0'
                        }
                    }
                }
            }

            stage('sonar-check') {
                when { expression { return  conf.getAttr('branchName') == 'dev' && conf.getAttr('codeLanguage') in  ['js', 'node'] && conf.getAttr('sonarCheck') && deployEnvironment != 'test' }}
                steps {
                    container('sonar-check') {
                        script {
                            codeCheck.sonarCheck()
                        }
                    }
                }
            }

            stage('Deploy-java') {
                agent { label 'mis-work-attendance' }
                when { expression { return conf.appName == 'work-attendance' } }
                steps {
                    bat '''
                        cd /D f:\\target
                        copy /Y work-attendance.jar d:\\attendance\\jar
                        cd /D d:\\attendance\\jar
                        run.bat
                        '''
                }
            }

//            stage('Download Config file') {
//                when { expression { return conf.getAttr('deploy') } }
//
//                steps {
//                    container('kubectl') {
//                        script {
//                            try {
//                                withCredentials([usernamePassword(credentialsId: 'passwd-zs', passwordVariable: 'password', usernameVariable: 'username')]) {
//                                    sh 'source /etc/profile; git config --global http.sslVerify false ; git clone https://$username:$password@gitlab.dm-ai.cn/application-engineering/devops/deployment.git'
//                                }
//                            } catch (e) {
//                                sh "echo ${e}"
//                            }
//                        }
//                    }
//                }
//            }

            stage('Deploy') {

                // 当项目的全局选项设置为deploy == true的时候，才进行部署的操作
//                when { expression { return conf.getAttr('deploy') } }
                when {
                    allOf
                            { expression { return conf.getAttr('deploy') }; expression { return deployEnvironment != 'test'} }
                }

                steps {
                    container('kubectl') {
                        script {
                            echo deployEnvironment
                            deploykubernetes.createConfigMap()
                            deploykubernetes.deployKubernetes()
                        }
                    }

                }
            }

            stage('Deploy test') {
                when { expression { return deployEnvironment == 'test' } }

//                    input {
//                        message "dev分支已经部署到开发环境，是否继续部署到测试环境？"
//                        ok "是的，我确认！"
//                    }

                    steps {
                        container('kubectl-test') {
                            script {
                                deploykubernetes.createConfigMapTest()
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
                        dmaiEmail.sendEmail('failure')
                    }
                }

                success {
                    script {
                        dmaiEmail.sendEmail('success')
                    }
                }
            }

        }
    }