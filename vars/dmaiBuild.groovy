import com.dmai.*

def call(Map map, env) {
    // 默认master 和 dev分支才进行构建
    if (!(env.BRANCH_NAME in ['master', 'dev', 'stage','release'])) return

    // 定义定义的全局的配置项目
    String appName = map.get('appName')
    Conf conf = new Conf(this, appName, map)

    // 把用户设置的全局的属性，加入到默认的全局的设置当中
    conf.setUserAttr(map)

    // 注入jenkins的环境变量到全局的Conf
    conf.setJenkinsAttrToConf(env, currentBuild)

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

    // init service app check
    KubernetesStatusCheck kubernetesStatusCheck = new KubernetesStatusCheck(this, conf)

    // default replicas
    def replicas = String.valueOf( conf.getAttr('replicas') ? conf.getAttr('replicas') : 1)

    // default cpu
    def defaultEnvType = conf.getAttr('envType') ? conf.getAttr('envType') : 'cpu'

    // cpuRequests
    def defaultCpuRequests = conf.getAttr('cpuRequests') ? conf.getAttr('cpuRequests') : ''

    // memoryRequests
    def defaultMemoryRequests = conf.getAttr('memoryRequests') ? conf.getAttr('memoryRequests') : ''

    // cpuLimits
    def defaultCpuLimits = conf.getAttr('cpuLimits') ? conf.getAttr('cpuLimits') : ''

    // memoryLimits
    def defaultMemoryLimits = conf.getAttr('memoryLimits') ? conf.getAttr('memoryLimits') : ''

    //
    if (conf.getAttr('branchName') == 'master' && conf.getAttr('master') !='prd') return

    //
    if (conf.appName == 'work-attendance' && conf.getAttr('branchName') != 'master') return

    // tmp 专门给高鹏
    if (conf.getAttr('branchName') == 'release') {
        if ( conf.getAttr('namespace') != 'x2-ta' ) {
            return
        } else {
            conf.setAttr('namespace', 'x2-ta-release')
            if (conf.getAttr('nodePort')) {
                conf.setAttr('nodePort', Integer.valueOf(conf.getAttr('nodePort')) + 300)
            }

            if (conf.getAttr('domain')) {
                conf.setAttr('domain', 'release-' + conf.getAttr('domain'))
            }

            conf.setAttr('branchName', 'dev')
        }
    }



    println('【开始进行构建】')
    pipeline {

        // 在整个构建之前，先进行参数化的设置
        parameters {
            choice(name: 'DEPLOY_ENV', choices: ['dev', 'test', 'lexue'], description: 'dev分支部署的环境，目前支持：dev/test/lexue, lexue 针对的是xmc2项目。')
            choice(name: 'ENV_TYPE', choices: [defaultEnvType, 'cpu','gpu', 'all'], description: 'cpu代表部署cpu服务器，gpu代表gpu服务器，all代表不做限制任意漂流')
            string(name: 'GIT_VERSION', defaultValue: 'last', description: 'git的commit 版本号，git log 查看。')
            string(name: 'VUE_APP_SCHOOL', defaultValue: 'S00001', description: '学校的Code，xmc2-frontend项目使用，其他不关注,s小写    ')
            choice(name: 'VUE_APP_SCENE', choices: ['main', 'training'], description: 'xmc2-frontend项目使用，其他不关注')
            string(name: 'DEPLOY_MASTER_PASSWORD', defaultValue: '部署master分支到线上环境，请找运维人员输入密码自动部署', description: '部署master分支请找运维人员输入密码自动部署')

            string(name: 'REPLICAS', defaultValue: replicas, description: '部署在k8s集群中需要的副本数')

            //算法专用，其他不用理会，
            string(name: 'MODEL_VERSION', defaultValue: 'latest', description: '算法模型专用，其他项目不用')


            // 资源限制
            string(name: 'CPU_REQUEST', defaultValue: defaultCpuRequests, description: '应用的cpu初始设置，示例：300m, 300分片，0.3核, 空代表不限制')
            string(name: 'CPU_LIMIT', defaultValue: defaultCpuLimits, description: '应用的cpu限制设置，示例：500m, 500分片，0.5核，空代表不限制')

            string(name: 'MEMORY_REQUEST', defaultValue: defaultMemoryRequests, description: '应用的内存初始设置，示例：500Mi, 使用内存500m，空代表不限制')
            string(name: 'MEMORY_LIMIT', defaultValue: defaultMemoryLimits, description: '应用的内存限制设置，示例：1000Mi, 使用内存1000m，空代表不限制')


        }

//        triggers {
//            cron('H/30 * * * *')
//        }

        // 转化为可用的环境变量
        environment {
            deployEnvironment = "${params.DEPLOY_ENV}"
            envType = "${params.ENV_TYPE}"
            gitVersion = "${params.GIT_VERSION}"
            vueAppScene = "${params.VUE_APP_SCENE}"
            vueAppSchool = "${params.VUE_APP_SCHOOL}"
            deployMasterPassword = "${params.DEPLOY_MASTER_PASSWORD}"
            userReplicas = "${params.REPLICAS}"
            cpuRequests = "${params.CPU_REQUEST}"
            cpuLimits = "${params.CPU_LIMIT}"
            memoryRequests = "${params.MEMORY_REQUEST}"
            memoryLimits = "${params.MEMORY_LIMIT}"
        }

        agent {
            kubernetes {
                cloud 'kubernetes-dev'
                label appName + '-' + conf.getAttr('branchName') + '-' + conf.getAttr('buildNumber')
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom 'base-template'
                yaml new JenkinsRunTemplate(conf).getJenkinsRunTemplate(params.DEPLOY_MASTER_PASSWORD, params.DEPLOY_ENV)
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

                        echo currentBuild.displayName
                        echo currentBuild.fullDisplayName
                        echo currentBuild.projectName
//                        echo currentBuild.fullProjectName

                        if (conf.getAttr('branchName') == 'master' && deployMasterPassword != 'dmai2019') {
                            throw "master分支请运维人员触发！"
                        }

                        if (conf.appName == 'xmc2-frontend') {
                            conf.setAppName(conf.appName + (vueAppScene == 'main' ? '' : '-' + vueAppScene) + (vueAppSchool == 'S00001' ? '' : '-' + vueAppSchool)  )
                            conf.setVueAppScene(vueAppScene)
                            conf.setVueAppSchool(vueAppSchool)

                            echo conf.vueAppScene
                            echo conf.vueAppSchool
                        }

                        conf.setAttr('replicas', userReplicas)
                            echo conf.getAttr('replicas')

                        conf.setAttr('dev', deployEnvironment)
                            echo deployEnvironment

                        conf.setAttr('envType', envType)
                            echo envType

                        if (conf.getAttr('dev') == 'lexue') {
                            conf.setockerRegistryHost('rdac-docker.dm-ai.cn')
                        }

                        conf.setAttr('cpuRequests', params.CPU_REQUEST)
                        conf.setAttr('memoryRequests', params.MEMORY_REQUEST)
                        conf.setAttr('cpuLimits', params.CPU_LIMIT)
                        conf.setAttr('memoryLimits', params.MEMORY_LIMIT)

                        // 算饭专用
                        conf.setModelVersion(params.MODEL_VERSION)
                    }
                }
            }

            stage('Specified version') {
                when {
                    allOf {
                        expression { return  gitVersion != 'last'};
                    }
                }
//                when { expression { return  gitVersion != 'last'} }
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
                when {
                    allOf {
                        expression { return  conf.getAttr('useCustomImage')};
                    }
                }
//                when { expression { return  conf.getAttr('useCustomImage')} }
                steps {
                    container('custom-image') {
                        sh conf.getAttr('execCommand')
                    }
                }
            }

            stage('Compile') {

                // 当项目的全局选项设置为compile == true的时候，才进行部署的操作
                when {
                    allOf {
                        expression { return conf.getAttr('compile') }
                    }
                }
//                when { expression { return conf.getAttr('compile') } }
                steps {
                    container('compile') {
                        script {
                            compile.compile()
                        }
                    }
                }
            }

            stage('Download Config file') {
                when {
                    allOf {
                        expression { return conf.getAttr('deploy') };
                    }
                }
//                when { expression { return conf.getAttr('deploy') } }
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
                when {
                    allOf {
                        expression { return  conf.getAttr('makeImage')};
                    }
                }
//                when { expression { return  conf.getAttr('makeImage')} }
                steps {
                    container('docker-compose') {
                        script {
                            makeDockerImage.makeImage()
                        }
                    }
                }
            }

            stage('Push Image') {
                when {
                    allOf {
                        expression { return  conf.getAttr('makeImage')}
                    }
                }
//                when { expression { return  conf.getAttr('makeImage')} }
                steps {
                    container('docker-compose') {
                        script {
                            makeDockerImage.pushImage()
                        }
                    }
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

                when {
                    allOf {
                        expression { return conf.getAttr('deploy') };
                        expression { return deployEnvironment != 'test'};
                    }
                }

                steps {
                    container('kubectl') {
                        script {

                            if (conf.getAttr('branchName') == 'master' && deployMasterPassword != 'dmai2019') {
                                throw "master分支请运维人员触发！"
                            }

                            echo deployEnvironment
                            deploykubernetes.createIngress()
                            deploykubernetes.createConfigMap()
                            deploykubernetes.deployKubernetes()
                        }
                    }

                }
            }

            stage('Deploy test') {
//                when { expression { return deployEnvironment == 'test' } }
                when {
                    allOf {
                        expression { return deployEnvironment == 'test' };
                    }
                }

//                    input {
//                        message "dev分支已经部署到开发环境，是否继续部署到测试环境？"
//                        ok "是的，我确认！"
//                    }

                    steps {
                        container('kubectl-test') {
                            script {
                                deploykubernetes.createIngress()
                                deploykubernetes.createConfigMapTest()
                                deploykubernetes.deployKubernetes()
                            }
                        }
                    }
                }

            stage('Check service') {
                steps {
                    container('kubectl') {
                        script {
                            sh "echo '检查部署在k8s集群中的服务的pod是否正常运行，等待限时120秒。"
                            if (kubernetesStatusCheck.waitKubernetesServerStarted() == true ) {
                                sh "echo '部署在k8s集群中的服务已正常运行'"
                            } else {
                                sh "echo '在120秒内，检查k8s集群中服务的pods的状态失败，请手动检查服务状态怒，构建失败'"
                                throw "please check service status with admin."
                            }
                        }
                    }
                }
            }

            stage('Install nyc') {
                when {
                    allOf {
                        expression { return conf.getAttr('branchName') == 'dev' };
                        expression { return  conf.getAttr('codeLanguage') in  ['js', 'node']};
                        expression { return  conf.getAttr('sonarCheck') };
                        expression { return  deployEnvironment != 'test' };
                    }
                }
//                when { expression { return  conf.getAttr('codeLanguage') in  ['js', 'node'] && conf.getAttr('sonarCheck') && deployEnvironment != 'test'}  }
                steps {
                    container('compile') {
                        script {
                            sh 'npm config set registry http://192.168.3.13:8081/repository/npm/ && yarn install || echo 0'
                            sh 'npm i -g nyc || echo 0'
                            sh 'npm i -g mocha || echo 0'
                            sh 'rm -fr deployment || echo 0'
                            sh 'nyc --reporter=lcov --reporter=text --report-dir=coverage mocha test/**/*.js --exit || echo 0'
                        }
                    }
                }
            }

            stage('sonar-check') {
                when {
                    allOf {
                        expression { return conf.getAttr('branchName') == 'dev' };
                        expression { return conf.getAttr('codeLanguage') in  ['js', 'node'] };
                        expression { return conf.getAttr('sonarCheck') };
                        expression { return deployEnvironment != 'test' };
                    }
                }
//                when { expression { return  conf.getAttr('branchName') == 'dev' && conf.getAttr('codeLanguage') in  ['js', 'node'] && conf.getAttr('sonarCheck') && deployEnvironment != 'test' }}
                steps {
                    container('sonar-check') {
                        script {
                            codeCheck.sonarCheck()
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