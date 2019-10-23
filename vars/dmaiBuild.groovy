import com.dmai.*
import com.tool.Tools

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

    // gpu_card_count
    def defaultGpuLimits = String.valueOf( conf.getAttr('gpuLimits') ? conf.getAttr('gpuLimits') : 0)

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

    //
    def deployEnv =  Tools.addItemToListHead(['prd', 'dev', 'test', 'stage', 'jenkins'], conf.getDeployEnv())

    //
    def topEnvType = Tools.addItemToListHead(['cpu','gpu', 'arm', 'all'], defaultEnvType)

    // namespace
    def defaultNamespace = conf.getAttr('namespace') ? conf.getAttr('namespace') : 'test'

    def topNamespace = Tools.addItemToListHead(['xmc2-lexue', 'xmc2-chongwen'], defaultNamespace)

    // git address
    def defaultGitAddress = conf.getAttr('gitAddress') ? conf.getAttr('gitAddress') : ''

    // compile
    def defaultCompile = conf.getAttr('compile') ? conf.getAttr('compile') : false

    // deploy
    def defaultDeploy = conf.getAttr('deploy') ? conf.getAttr('deploy') : false


    // code language
    def defaultCodeLanguage = conf.getAttr('codeLanguage') ? conf.getAttr('codeLanguage') : ''

    // domain
    def defaultDomain = conf.getAttr('domain') ? conf.getAttr('domain') : ''

    // if_use_auto_deploy_file
    def useAutoDeployFile = conf.getAttr('customKubernetesDeployTemplate') ? conf.getAttr('customKubernetesDeployTemplate') : false

    // auto deploy content
    def autoDeployContent = conf.getAttr('autoDeployContent') ? conf.getAttr('autoDeployContent') : ''

    // customDockerfile
    def useCustomDockerFile = conf.getAttr('customDockerfile') ? conf.getAttr('customDockerfile') : false

    // custom dockerfile content
    def customDockerfileContent = conf.getAttr('customDockerfileContent') ? conf.getAttr('customDockerfileContent') : ''

    // sevice type
    def defaultServiceType = conf.getAttr('svcType') ? conf.getAttr('svcType') : 'ClusterIP'
    def toDefaultServiceType = Tools.addItemToListHead(['ClusterIP', 'NodePort', 'None'], defaultServiceType)

    // use service
    def useService = conf.getAttr('useService') ? conf.getAttr('useService') : true

    // k8sKind
    def defaultK8sKind = conf.getAttr('k8sKind') ? conf.getAttr('k8sKind') :'deployment'
    def toK8sKind = Tools.addItemToListHead(['Deployment', 'StatefulSet'], defaultK8sKind)

    // make images
    def defaultMakeImage = conf.getAttr('makeImage') ? conf.getAttr('makeImage') : false

    // if check pods service
    def defaultCheckPodsStatus = true

    println('【开始进行构建】')
    pipeline {

        // 在整个构建之前，先进行参数化的设置
        parameters {
            choice(name: 'DEPLOY_ENV', choices: deployEnv, description: 'dev分支部署的环境，目前支持：prd/dev/test/stage, lexue 针对的是xmc2项目。')
            choice(name: 'ENV_TYPE', choices: topEnvType, description: 'cpu代表部署cpu服务器，gpu代表gpu服务器，all代表不做限制任意漂流')
            string(name: 'GPU_CARD_COUNT', defaultValue: defaultGpuLimits, description: '使用gpu卡的时候，在k8s集群中，一个pods使用的gpu卡的限制。')

            string(name: 'GIT_VERSION', defaultValue: 'last', description: 'git的commit 版本号，git log 查看。')
            choice(name: 'VUE_APP_SCHOOL', choices: ['chongwen', 'S00001'], description: '学校的Code，xmc2-frontend项目使用，其他不关注,s小写    ')
            choice(name: 'VUE_APP_SCENE', choices: ['school', 'agency'], description: 'xmc2-frontend项目使用，其他不关注')
            choice(name: 'NODE_ENV', choices: ['dev', 'prod', 'test', 'stage'], description: '前端专用，其他不关注')

            string(name: 'DEPLOY_MASTER_PASSWORD', defaultValue: 'please-input-password', description: '部署master分支请找运维人员输入密码自动部署')
            string(name: 'COMPILE_PARAM', defaultValue: '', description: 'android项目自定义的编译参数')

            string(name: 'REPLICAS', defaultValue: replicas, description: '部署在k8s集群中需要的副本数')

            //算法专用，其他不用理会，
            string(name: 'MODEL_VERSION', defaultValue: 'latest', description: '算法模型专用，其他项目不用')

            // 资源限制
            string(name: 'CPU_REQUEST', defaultValue: defaultCpuRequests, description: '应用的cpu初始设置，示例：300m, 300分片，0.3核, 空代表不限制')
            string(name: 'CPU_LIMIT', defaultValue: defaultCpuLimits, description: '应用的cpu限制设置，示例：500m, 500分片，0.5核，空代表不限制')

            string(name: 'MEMORY_REQUEST', defaultValue: defaultMemoryRequests, description: '应用的内存初始设置，示例：500Mi, 使用内存500m，空代表不限制')
            string(name: 'MEMORY_LIMIT', defaultValue: defaultMemoryLimits, description: '应用的内存限制设置，示例：1000Mi, 使用内存1000m，空代表不限制')

            // 自定义 appName
            string(name: 'APP_NAME', defaultValue: appName, description: '可以自定义appName，特殊场景，例如：xmc-model-serving-student/xmc-model-serving-teacher。')

            // namespace
            string(name: 'NAMESPACE', defaultValue: defaultNamespace, description: '应用部署的时候，k8s使用的namespace， 可自定义namespace,例如：xmc2-lexue/xmc2-chongwen')

            // git address
            string(name: 'GIT_ADDRESS', defaultValue: defaultGitAddress, description: '应用的git 代码 地址')

            // codeLanguage
            string(name: 'CODE_LANGUAGE', defaultValue: defaultCodeLanguage, description: 'code language')

            //
            booleanParam(name: 'COMPILE', defaultValue: defaultCompile, description: '是否编译')

            //
            booleanParam(name: 'DEPLOY', defaultValue: defaultDeploy, description: '是否部署')

            //domain
            string(name: 'DOMAIN', defaultValue: defaultDomain, description: '应用使用的域名')

            // if_use_auto_deploy_file
            booleanParam(name: 'CUSTOM_KUBERNETES_DEPLOY_TEMPLATE', defaultValue: useAutoDeployFile, description: '使用使用自定义的k8s部署模版')

            //  auto deploy content
            string(name: 'CUSTOM_KUBERNETES_DEPLOY_TEMPLATE_CONTENT', defaultValue: autoDeployContent, description: '自定义模版内容')

            // customDockerfile
            booleanParam(name: 'CUSTOM_DOCKERFILE', defaultValue: useCustomDockerFile, description: '是否使用自定义的dockerfile')

            // custom dockerfile content
            text(name: 'CUSTOM_DOCKERFILE_CONTENT', defaultValue: customDockerfileContent, description: '自定义的dockerfile内容')

            // service type
            choice(name: 'SERVICE_TYPE', choices: toDefaultServiceType, description: '项目默认使用的服务的类型')

            // use service
            booleanParam(name: 'USE_SERVICE', defaultValue: useService, description: '是否使用service')

            // k8s kind
            choice(name: 'K8S_KIND', choices: toK8sKind, description: 'k8s使用的kind的类型')

            // if make images
            booleanParam(name: 'IF_MAKE_IMAGE', defaultValue: defaultMakeImage, description: '是否制作镜像')

            // success deploy, check pods status
            booleanParam(name: 'IF_CHECK_PODS_STATUS', defaultValue: defaultCheckPodsStatus, description: '是否在部署后检查pods的状态')

        }

//        triggers {
//            cron('H/30 * * * *')
//        }

        // 转化为可用的环境变量
        environment {
            gitVersion = "${params.GIT_VERSION}"
            deployMasterPassword = "${params.DEPLOY_MASTER_PASSWORD}"
        }

        agent {
            kubernetes {
                cloud 'kubernetes-dev'
                label appName + '-' + conf.getAttr('branchName') + '-' + conf.getAttr('buildNumber')
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom 'base-template'
                yaml new JenkinsRunTemplate(conf).getJenkinsRunTemplate(params.DEPLOY_MASTER_PASSWORD, params.DEPLOY_ENV, params)
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

//                        echo currentBuild.displayName
//                        echo currentBuild.fullDisplayName
//                        echo currentBuild.projectName
//                        echo currentBuild.fullProjectName

                        if (conf.getAttr('branchName') == 'master' && deployMasterPassword != 'dmai2019999') {
                            throw "master分支请运维人员触发！"
                        }

                        // print all data
                        println(conf.printAppConf())


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
                    anyOf {
                        expression { return conf.getAttr('compile') };
                        expression { return conf.getAttr('codeLanguage') == 'node' };

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

            stage('Download Model') {
                when {
                    allOf {
                        expression { return conf.getAttr('useModel') };
                        expression { return conf.getAttr('modelGitAddress') };
                    }
                }
                steps {
                    container('kubectl') {
                        script {
                            try {
                                withCredentials([usernamePassword(credentialsId: 'dev-admin-model', passwordVariable: 'password', usernameVariable: 'username')]) {
                                    sh 'source /etc/profile; git config --global http.sslVerify false ; git clone http://$username:$password@' + conf.getAttr("modelGitAddress").replace("http://", "") + ' model'
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
                        expression { return conf.getAttr('deployEnv') != 'test'};
                    }
                }

                steps {
                    container('kubectl') {
                        script {

                            if (conf.getAttr('branchName') == 'master' && deployMasterPassword != 'dmai2019999') {
                                throw "master分支请运维人员触发！"
                            }

                            echo conf.getAttr('deployEnv')
                            deploykubernetes.createIngress()
                            deploykubernetes.createConfigMap()
                            deploykubernetes.deployKubernetes()
                        }
                    }

                }
            }

            stage('Deploy test') {
                when {
                    allOf {
                        expression { return conf.getAttr('deploy') };
                        expression { return conf.getAttr('deployEnv') == 'test' };
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
                when {
                    allOf {
                        expression { return conf.getAttr('deploy') };
                        expression { return conf.getAttr('checkPodsStatus') }
                    }
                }
//                when { expression { return conf.getAttr('deploy') } }
                steps {
                    container('kubectl') {
                        script {
                            sh "echo '检查部署在k8s集群中的服务的pod是否正常运行，等待限时120秒。'"
                            if (kubernetesStatusCheck.waitKubernetesServerStarted() == true ) {
                                sh "echo '部署在k8s集群中的服务已正常运行'"
                            } else {
                                sh "echo '在120秒内，检查k8s集群中服务的pods的状态失败，请手动检查服务部署后的pod状态，构建失败！！'"
                                throw "please check service status with admin."
                            }
                        }
                    }
                }
            }

            stage('Install nyc') {
                when {
                    allOf {
                        expression { return conf.getAttr('deploy') };
                        expression { return conf.getAttr('branchName') == 'dev' };
                        expression { return  conf.getAttr('codeLanguage') in  ['js', 'node']};
                        expression { return  conf.getAttr('sonarCheck') };
                        expression { return  conf.getAttr('deployEnv') != 'test' };
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
                        expression { return conf.getAttr('deploy') };
                        expression { return conf.getAttr('branchName') == 'dev' };
                        expression { return conf.getAttr('codeLanguage') in  ['js', 'node'] };
                        expression { return conf.getAttr('sonarCheck') };
                        expression { return conf.getAttr('deployEnv') != 'test' };
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