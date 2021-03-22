import com.dmai.*
import com.tool.Tools

def call(Map map, env) {
    // 默认master 和 dev分支才进行构建
//    if (!(env.BRANCH_NAME in ['master', 'dev', 'stage','release'])) return

    // 定义定义的全局的配置项目, 兼容Jenkinsfile，没有 appName这行
    String appName
    boolean containsKey = map.containsKey('appName') //containsKey是否包含某个key
    if (containsKey) {
        appName = map.get('appName')
    } else {
        appName = 'common-build-name'
    }

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
    def replicas = String.valueOf(conf.getAttr('replicas') ? conf.getAttr('replicas') : 1)

    // default containerPort
    def containerPort = String.valueOf(conf.getAttr('containerPort') ? conf.getAttr('containerPort') : 80)

    // default cpu
    def defaultEnvType = conf.getAttr('envType') ? conf.getAttr('envType') : 'cpu'

    // default env 状态,在线环境和离线环境（公司外部
    def defaultEnvStatus = conf.getAttr('deployEnvStatus') ? conf.getAttr('deployEnvStatus') : 'start'

    // gpu_card_count
    def defaultGpuLimits = String.valueOf(conf.getAttr('gpuLimits') ? conf.getAttr('gpuLimits') : 0)

    // gpu_mem_count
    def defaultGpuMemLimits = String.valueOf(conf.getAttr('gpuMemLimits') ? conf.getAttr('gpuMemLimits') : 0)


    def defaultGpuType = conf.getAttr('gpuType') ? conf.getAttr('gpuType') : ''


    // cpuRequests
    def defaultCpuRequests = conf.getAttr('cpuRequests') ? conf.getAttr('cpuRequests') : ''

    // memoryRequests
    def defaultMemoryRequests = conf.getAttr('memoryRequests') ? conf.getAttr('memoryRequests') : ''

    // cpuLimits
    def defaultCpuLimits = conf.getAttr('cpuLimits') ? conf.getAttr('cpuLimits') : ''

    // memoryLimits
    def defaultMemoryLimits = conf.getAttr('memoryLimits') ? conf.getAttr('memoryLimits') : ''

    //
//    if (conf.getAttr('branchName') == 'master' && conf.getAttr('master') !='prd') return

    //work-attendance 项目特殊处理
    if (conf.appName == 'work-attendance' && conf.getAttr('branchName') != 'master') {
        return
    }

    //
    def deployEnv = Tools.addItemToListHead(['prd', 'dev', 'test', 'stage', 'jenkins', 'mlcloud-dev', 'lexue', 'tuoke', 'not-deploy'], conf.getDeployEnv())

    // default node env
    //    def defaultNodeEnvList = Tools.addItemToListHead(['dev', 'prod', 'test', 'stage', 'jenkins', 'mlcloud-dev', 'lexue', 'tuoke', 'not-deploy'], conf.getDeployEnv() ? conf.getDeployEnv().replaceAll('prd', 'prod') : 'dev')
    def defaultNodeEnvList = Tools.addItemToListHead(['dev', 'prd', 'test', 'stage', 'jenkins', 'mlcloud-dev', 'lexue', 'tuoke', 'not-deploy'], conf.getDeployEnv())

    //
    def topEnvType = Tools.addItemToListHead(['cpu', 'gpu', 'arm', 'all'], defaultEnvType)

    def topEnvStatus = Tools.addItemToListHead(['start', 'stop'], defaultEnvStatus)

    // namespace
    def defaultNamespace = conf.getAttr('namespace') ? conf.getAttr('namespace') : 'test'

    // def topNamespace = Tools.addItemToListHead(['xmc2-lexue', 'xmc2-chongwen'], defaultNamespace)

    // git address
    def defaultGitAddress = conf.getAttr('gitAddress') ? conf.getAttr('gitAddress') : ''

    // compile
    def defaultCompile = conf.getAttr('compile') ? conf.getAttr('compile') : false
    if (conf.getAttr('codeLanguage') in ['node', 'nodets']) {
        defaultCompile = true
    }

    // https
    def defaultUseHttps = conf.getAttr('https') ? conf.getAttr('https') : false

    def defaultUserApolloOfflineEnv = conf.getAttr('ifUseApolloOfflineEnv') ? conf.getAttr('ifUseApolloOfflineEnv') : false

    // http
    def defaultUseHttp = conf.getAttr('http') ? conf.getAttr('http') : true

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


    // k8sKind
    def defaultcontrollerType = conf.getAttr('replicationControllerType') ? conf.getAttr('replicationControllerType') : 'deployment'
    def controllerType = Tools.addItemToListHead(['Deployment', 'StatefulSet'], defaultcontrollerType)

    // make images
    def defaultMakeImage = conf.getAttr('makeImage') ? conf.getAttr('makeImage') : false

    // 是否使用模型
    def defaultUseModel = (conf.getAttr('useModel')) ? (conf.getAttr('useModel')) : false

    // 是否使用configmap注入环境变量
    def defaultUseConfigmap = conf.getAttr('useConfigMap') ? conf.getAttr('useConfigMap') : false

    // 是否有存储需求
    def defaultUseStore = conf.getAttr('useStore') ? conf.getAttr('useStore') : false

    // 存储路径
    def defaultStorePath = conf.getAttr('storePath') ? conf.getAttr('storePath') : '/data'

    // 分支名称
    def branchName = conf.getAttr('branchName') ? conf.getAttr('branchName') : ''

    // buildPlatform
    def defBuildPlatform = conf.getAttr('buildPlatform') ? conf.getAttr('buildPlatform') : 'jenkins'

    // if check pods service
    def defaultCheckPodsStatus = false

    println('【开始进行构建】')
    // def tool = new com.tool.print()
    // tool.PrintMes('【开始进行构建】', 'green')
    pipeline {
        // 在整个构建之前，先进行参数化的设置
        parameters {
            choice(name: 'DEPLOY_ENV', choices: deployEnv, description: 'dev分支部署的环境，目前支持：prd/dev/test/stage, lexue 针对的是xmc2项目。')
            choice(name: 'ENV_TYPE', choices: topEnvType, description: 'cpu代表部署cpu服务器，gpu代表gpu服务器，all代表不做限制任意漂流')
            choice(name: 'DEPLOY_ENV_STATUS', choices: topEnvStatus, description: 'start代表在线环境，stop代表离线环境(公司外部部署)')
            choice(name: 'GPU_CONTROL_MODE', choices: ['pod', 'mem'], description: 'pod代表以gpu的卡的数量去绑定应用，mem代表以gpu的内存去绑定应用')
            string(name: 'GPU_CARD_COUNT', defaultValue: defaultGpuLimits, description: '使用gpu卡的时候，在k8s集群中，一个pods使用的gpu卡的限制。')
            string(name: 'GPU_MEM_COUNT', defaultValue: defaultGpuMemLimits, description: '使用gpu卡的时候，在k8s集群中，一个pods使用的gpu卡的内存的限制。')
            
            string(name: 'GPU_TYPE', defaultValue: defaultGpuType, description: 'gpu卡类型选择')

            //            string(name: 'VERSION_CONTROL_MODE', defaultValue: 'GitCommitId', description: '构建的时候的版本控制方式，GitCommitId和GitTags，默认GitCommitId')
            choice(name: 'VERSION_CONTROL_MODE', choices: ['GitCommitId', 'GitTags'], description: '构建的时候的版本控制方式，GitCommitId和GitTags，默认GitCommitId')
            string(name: 'GIT_VERSION', defaultValue: 'last', description: 'git的commit 版本号，git log 查看。')
            string(name: 'GIT_TAG', defaultValue: '', description: 'git的tag版本')
            string(name: 'APOLLO_CLUSTER_NAME', defaultValue: 'default', description: 'apollo配置中心中的集群名字，默认是default')
            string(name: 'APOLLO_NAMESPACE', defaultValue: 'application', description: 'apollo配置中心中的空间名，默认是application')
            booleanParam(name: 'IF_USE_APOLLO_OFFLINE_ENV', defaultValue: defaultUserApolloOfflineEnv , description: '是否从apollo拉取配置注入环境变量，默认是false')

            string(name: 'BRANCH_NAME', defaultValue: branchName, description: '代码分支名')

            choice(name: 'VUE_APP_SCENE', choices: ['school', 'agency'], description: 'xmc2-frontend项目使用，其他不关注')
            choice(name: 'NODE_ENV', choices: defaultNodeEnvList, description: '前端专用，其他不关注')

            string(name: 'DEPLOY_MASTER_PASSWORD', defaultValue: 'please-input-password', description: '部署master分支请找运维人员输入密码自动部署')
            string(name: 'COMPILE_PARAM', defaultValue: '', description: 'android项目自定义的编译参数')

            string(name: 'REPLICAS', defaultValue: replicas, description: '部署在k8s集群中需要的副本数')
            string(name: 'CONTAINER_PORT', defaultValue: containerPort, description: '默认的容器监听端口')

            //算法专用，其他不用理会，
            string(name: 'MODEL_VERSION', defaultValue: 'latest', description: '算法模型专用，其他项目不用')

            // 资源限制
            string(name: 'CPU_REQUEST', defaultValue: defaultCpuRequests, description: '应用的cpu初始设置，示例：300m, 300分片，0.3核, 空代表不限制')
            string(name: 'CPU_LIMIT', defaultValue: defaultCpuLimits, description: '应用的cpu限制设置，示例：500m, 500分片，0.5核，空代表不限制')

            string(name: 'MEMORY_REQUEST', defaultValue: defaultMemoryRequests, description: '应用的内存初始设置，示例：500Mi, 使用内存500m，空代表不限制')
            string(name: 'MEMORY_LIMIT', defaultValue: defaultMemoryLimits, description: '应用的内存限制设置，示例：1000Mi, 使用内存1000m，空代表不限制')

            // 自定义 appName
            string(name: 'APP_NAME', defaultValue: appName, description: '可以自定义appName，特殊场景，例如：xmc-model-serving-student/xmc-model-serving-teacher/tk-engine-image-process-teacher/tk-engine-image-process-student。')

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

            // https
            booleanParam(name: 'IF_USE_HTTPS', defaultValue: defaultUseHttps, description: '是否使用https')

            // http
            booleanParam(name: 'IF_USE_HTTP', defaultValue: defaultUseHttp, description: '是否使用http')

            // if_use_auto_deploy_file
            booleanParam(name: 'CUSTOM_KUBERNETES_DEPLOY_TEMPLATE', defaultValue: useAutoDeployFile, description: '使用使用自定义的k8s部署模版')

            //  auto deploy content
            text(name: 'CUSTOM_KUBERNETES_DEPLOY_TEMPLATE_CONTENT', defaultValue: autoDeployContent, description: '自定义模版内容')

            // customDockerfile
            booleanParam(name: 'CUSTOM_DOCKERFILE', defaultValue: useCustomDockerFile, description: '是否使用自定义的dockerfile')

            // custom dockerfile content
            text(name: 'CUSTOM_DOCKERFILE_CONTENT', defaultValue: customDockerfileContent, description: '自定义的dockerfile内容')

            // service type
            choice(name: 'SERVICE_TYPE', choices: toDefaultServiceType, description: '项目默认使用的服务的类型')

            // k8s ControllerType
            choice(name: 'replicationControllerType', choices: controllerType, description: 'k8s控制器类型')

            // if make images
            booleanParam(name: 'IF_MAKE_IMAGE', defaultValue: defaultMakeImage, description: '是否制作镜像')

            // defaultUseModel
            booleanParam(name: 'USE_MODEL', defaultValue: defaultUseModel, description: '是否使用模型文件')

            //
            booleanParam(name: 'USE_CONFIGMAP', defaultValue: defaultUseConfigmap, description: '是否使用configmap注入环境变量')

            //
            booleanParam(name: 'IF_STORAGE_LOCALE', defaultValue: defaultUseStore, description: '是否使用本地存储')

            //
            string(name: 'STORAGE_PATH', defaultValue: defaultStorePath, description: '使用的本地存储路径')

            // success deploy, check pods status
            booleanParam(name: 'IF_CHECK_PODS_STATUS', defaultValue: defaultCheckPodsStatus, description: '是否在部署后检查pods的状态')

            // set build platform
            string(name: 'BUILD_PLATFORM', defaultValue: defBuildPlatform, description: '构建平台，默认jenkins，adp代表发布平台，为了额兼容性考虑')

            //JS version
            string(name: 'JS_VERSION', defaultValue: '0.0.0', description: '前端库的版本，用于推流，其他项目不关注')

            // GLOABL_STRING
            string(name: 'GLOABL_STRING', defaultValue: '', description: '传递的特殊参数字符串')
        }


        // 转化为可用的环境变量
        environment {
            gitVersion = "${params.GIT_VERSION}"
            deployMasterPassword = "${params.DEPLOY_MASTER_PASSWORD}"
        }

        agent {
            kubernetes {
                yaml new JenkinsRunTemplate(conf).getJenkinsRunTemplate(params.DEPLOY_MASTER_PASSWORD, params.DEPLOY_ENV, params)
                cloud 'kubernetes-dev'
                label conf.getAttr('jobName') + '-' + Tools.handleBranchName(conf.getAttr('branchName')) + '-' + conf.getAttr('buildNumber')
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom 'base-template'
            }
        }

        
        options {
            timestamps() //日志会显示时间
            timeout(time: 1, unit: 'HOURS') // 设置任务的超时时间为1个小时。
            buildDiscarder(logRotator(numToKeepStr: '200')) //保留最近200个构建
        }

        stages {
            stage('初始化') {
                steps {
                    script {
                        conf.printAppConf()
                        withEnv(conf.withEnvList) {
                            sh 'printenv'
                        }
                    }
                }
            }

            stage('代码管理') {
                parallel {

                    stage('apidoc功能支持') {
                        when {
                            allOf {
                                expression { return conf.ifBuild() };
                                expression { return conf.getAttr('deployEnv') == 'prd' };
                                expression { return conf.getAttr('codeLanguage') in ['nodejs','java'] };
                            }
                        }
                        steps {
                            container('adp') {
                                script {
                                    try {
                                        sh 'git clone https://gitlab.dm-ai.cn/MSF/java/dm-api-doc.git /tmp/dm-api-doc'
                                        sh 'cd /tmp/dm-api-doc && timeout -t 60 sh -x apidoc.sh ' + conf.jenkinsWorkPath()
                                    } catch (e) {
                                        sh 'echo ${e}'
                                    }
                                }
                            }
                        }
                    }
                    stage('git切换到对应的commitID') {
                        when {
                            allOf {
                                expression { return conf.ifBuild() }
                                expression { return conf.getAttr('versionControlMode') == 'GitCommitId' }
                                // expression { return gitVersion != 'last' };
                            }
                        }
                        steps {
                            container('adp') {
                                script {
                                    if (conf.getAttr('gitVersion') == 'last') {
                                        conf.setAttr('gitVersion', env.GIT_COMMIT)
                                        conf.printAppConf()
                                    } else {
                                        sh 'git config --global http.sslVerify false ; git reset --hard "${gitVersion}"'
                                    }
                                }
                            }
                        }
                    }
                    stage('git切换到对应的tag') {
                        when {
                            allOf {
                                expression { return conf.ifBuild() };
                                expression { return conf.getAttr('versionControlMode') == 'GitTags' };
                            }
                        }
                        steps {
                            container('adp') {
                                script {
                                    try {
                                            sh "git config --global http.sslVerify false ;git fetch ;git checkout ${conf.getAttr('gitTag')}"
                                    } catch (e) {
                                        sh 'echo ${e}'
                                        conf.failMsg = '拉取指定git的tag失败，请确认tag是否正确'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                }
            }

            stage('代码检查'){
                when {
                    allOf { //所有的条件都满足
                        expression { return conf.ifBuild() };
                        expression { return conf.getAttr('deploy') };
                        expression { return conf.getAttr('branchName') == 'dev' };
                        expression { return conf.getAttr('codeLanguage') in ['js', 'node'] };
                        expression { return conf.getAttr('sonarCheck') };
                        expression { return conf.getAttr('deployEnv') != 'test' };
                    }
                }
                steps {
                    container('compile') {
                        script {
                            try {
                                sh 'npm config set registry https://npm.dm-ai.cn/repository/npm/ && npm install && npm run build || echo '
                                sh 'npm i -g nyc || echo 0'
                                sh 'npm i -g mocha || echo 0'
                                sh 'rm -fr deployment || echo 0'
                                sh 'nyc --reporter=lcov --reporter=text --report-dir=coverage mocha test/**/*.js --exit || echo 0'
                            } catch (e) {
                                sh "echo ${e}"
                            }

                            try {
                                    codeCheck.sonarCheck()
                            } catch (e) {
                                sh "echo ${e}"
                            }
                        }
                    }
                }
                    
            }
            stage('GPU模型文件处理') {
                when {
                    allOf {
                        expression { return conf.getAttr('useModel') }
                    }
                }
                parallel {
                    stage('使用git管理模型') {
                        when {
                            allOf {
                                expression { return conf.getAttr('ifUseGitManagerModel') }
                            }
                        }

                        steps {
                            container('adp') {
                                script {
                                    try {
                                        withCredentials([usernamePassword(credentialsId: 'devops-use', passwordVariable: 'password', usernameVariable: 'username')]) {
                                            sh 'source /etc/profile; git config --global http.sslVerify false ; git clone ' + conf.getAttr("modelGitRepository").replace("https://", 'https://$username:$password@') + ' model'
                                        }
                                        sh 'pwd;ls -l;rm -fr model/.git'
                                    } catch (e) {
                                        sh "echo ${e}"
                                        conf.failMsg = '从gitlab下载模型文件失败！'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                    // stage('使用文件存储管理模型') {
                    //     when {
                    //         allOf {
                    //             expression { return conf.getAttr('ifUseGitManagerModel') }
                    //         }
                    //     }
                    //     steps {
                    //         container('adp') {
                    //             script {
                    //                sh "mkdir -p ${this.conf.getAttr('modelPath')}; cp -rp /models/* ${this.conf.getAttr('modelPath')}"
                    //             }
                    //         }
                    //     }
                    // }
                }
            }

            stage('编译') {
                // 当项目的全局选项设置为compile == true的时候，才进行编译的操作
                when {
                    allOf {
                        expression { return conf.ifBuild() }
                    }
                }

                parallel {
                    stage('自定义编译') {
                        when {
                            allOf {
                                expression { return conf.getAttr('useCustomImage') }
                            }
                        }
                        steps {
                            container('custom-image') {
                                script {
                                    try {
                                        sh conf.getAttr('execCommand')
                                    } catch (e) {
                                        sh 'echo ${e}'
                                        conf.failMsg = '自定义镜像执行命令失败，执行命令为：' + conf.getAttr('execCommand')
                                        throw e
                                    }
                                }
                            }
                        }
                    }

                    // stage('通用编译') {
                    //     when {
                    //         allOf{
                    //             expression { return conf.getAttr('compile') }
                    //             expression { return conf.getAttr('codeLanguage') != 'golang' }
                    //             expression { return conf.getAttr('codeLanguage') != 'java' }
                    //             expression { return conf.getAttr('codeLanguage') != 'node' }
                    //             expression { return conf.getAttr('codeLanguage') != 'c++' }
                    //             expression { return conf.getAttr('codeLanguage') != 'android' }
                    //         }
                    //     }
                    //     steps {
                    //         container('compile') {
                    //             script {
                    //                 try {
                    //                     withEnv(conf.withEnvList){
                    //                         compile.compile()
                    //                     }
                    //                 } catch (e) {
                    //                     sh "echo ${e}"
                    //                     conf.failMsg = '编译失败！'
                    //                     throw e
                    //                 }
                    //             }
                    //         }
                    //     }
                    // }
                    stage('Nodejs编译') {
                        when {
                            allOf {
                                expression { return conf.getAttr('compile') }
                                expression { return conf.getAttr('codeLanguage') == 'node' }
                            }
                            
                        }
                        steps {
                            container('compile') {
                                script {
                                    try {
                                        withEnv(conf.withEnvList){
                                            compile.compileOfNodejs()
                                        }
                                    } catch (e) {
                                        sh "echo ${e}"
                                        conf.failMsg = '编译失败！'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                    stage('JavaScript编译') {
                        when {
                            allOf {
                                expression { return conf.getAttr('compile') }
                                expression { return conf.getAttr('codeLanguage') == 'js' }
                            }
                            
                        }
                        steps {
                            container('compile') {
                                script {
                                    try {
                                        withEnv(conf.withEnvList){
                                            compile.compileOfJs()
                                        }
                                    } catch (e) {
                                        sh "echo ${e}"
                                        conf.failMsg = '编译失败！'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                    stage('Nodets编译') {
                        when {
                            allOf {
                                expression { return conf.getAttr('compile') }
                                expression { return conf.getAttr('codeLanguage') == 'nodets' }
                            }
                            
                        }
                        steps {
                            container('compile') {
                                script {
                                    try {
                                        withEnv(conf.withEnvList){
                                            compile.compileOfNodets()
                                        }
                                    } catch (e) {
                                        sh "echo ${e}"
                                        conf.failMsg = '编译失败！'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                    stage('Java编译') {
                        when {
                            allOf {
                                expression { return conf.getAttr('compile') }
                                expression { return conf.getAttr('codeLanguage') == 'java' }
                            }
                            
                        }
                        steps {
                            container('compile') {
                                script {
                                    try {
                                        withEnv(conf.withEnvList){
                                            compile.compileOfJava()
                                        }
                                    } catch (e) {
                                        sh "echo ${e}"
                                        conf.failMsg = '编译失败！'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                    stage('Golang编译') {
                        when {
                            allOf {
                                expression { return conf.getAttr('compile') }
                                expression { return conf.getAttr('codeLanguage') == 'golang' }
                            }
                            
                        }
                        steps {
                            container('compile') {
                                script {
                                    try {
                                        withEnv(conf.withEnvList){
                                            compile.compileOfGolang()
                                        }
                                    } catch (e) {
                                        sh "echo ${e}"
                                        conf.failMsg = '编译失败！'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                    stage('C++编译') {
                        when {
                            allOf {
                                expression { return conf.getAttr('compile') }
                                expression { return conf.getAttr('codeLanguage') == 'c++' }
                            }
                            
                        }
                        steps {
                            container('compile') {
                                script {
                                    try {
                                        withEnv(conf.withEnvList){
                                            compile.compileOfC()
                                        }
                                    } catch (e) {
                                        sh "echo ${e}"
                                        conf.failMsg = '编译失败！'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                    stage('Android编译') {
                        when {
                            allOf {
                                expression { return conf.getAttr('compile') }
                                expression { return conf.getAttr('codeLanguage') == 'android' }
                            }
                            
                        }
                        steps {
                            container('compile') {
                                script {
                                    try {
                                        withEnv(conf.withEnvList){
                                            compile.compileOfAndroid()
                                        }
                                    } catch (e) {
                                        sh "echo ${e}"
                                        conf.failMsg = '编译失败！'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                    stage('Unity编译') {
                        when {
                            allOf {
                                expression { return conf.getAttr('compile') }
                                expression { return conf.getAttr('codeLanguage') == 'unity' }
                            }
                            
                        }
                        steps {
                            container('compile') {
                                script {
                                    try {
                                        withEnv(conf.withEnvList){
                                            compile.compileOfUnity()
                                        }
                                    } catch (e) {
                                        sh "echo ${e}"
                                        conf.failMsg = '编译失败！'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                    
                    
                }
            }

            stage('切换镜像仓库地址'){
                parallel {
                    stage('公司对外rdac仓库') {
                        when {
                            allOf {
                                expression { return conf.ifBuild() }
                                expression { return conf.getAttr('deployEnvStatus') == 'stop' }
                                expression { return conf.getAttr('deployEnv') != 'not-deploy' }
                                expression { return conf.getAttr('deployEnv') != 'chuanyin' }
                                expression { return conf.getAttr('codeLanguage') != 'android'}
                                expression { return conf.getAttr('codeLanguage') != 'unity' }
                            }
                        }
                        steps {
                            container('adp') {
                                script {
                                    conf.setAttr('buildImageTag', conf.getBuildImageAddressTag())
                                    conf.setAttr('buildImageAddress', conf.getBuildImageAddress('rdac-docker.dm-ai.cn'))
                                    conf.printAppConf()
                                    makeDockerImage.makeDockerComposeYml()
                                }
                            }
                        }
                    }
                    stage('阿里云华北3仓库') {
                        when {
                            allOf {
                                expression { return conf.ifBuild() }
                                expression { return conf.getAttr('deployEnvStatus') == 'stop' }
                                expression { return conf.getAttr('deployEnv') == 'not-deploy' }
                                expression { return conf.getAttr('appName') in ['base-dingding-api-gateway','base-dingding-auth-service','base-dingding-message-service','base-dingding-tuoke-live-classroom','base-dingding-frontend','base-dingding-content-security'] }
                            }
                        }
                        steps {
                            container('adp') {
                                script {
                                    conf.setAttr('buildImageTag', conf.getBuildImageAddressTag())
                                    conf.setAttr('buildImageAddress', conf.getBuildImageAddress('registry.cn-zhangjiakou.aliyuncs.com'))
                                    conf.printAppConf()
                                    makeDockerImage.makeDockerComposeYml()
                                }
                            }
                        }
                    }
                    stage('公司内部仓库') {
                        when {
                            anyOf{
                                allOf {
                                    expression { return conf.ifBuild() }
                                    expression { return conf.getAttr('deployEnvStatus') == 'start'}
                                    expression { return conf.getAttr('codeLanguage') != 'android'}
                                    expression { return conf.getAttr('codeLanguage') != 'unity' }
                                }
                                allOf {
                                    expression { return conf.ifBuild() }
                                    expression { return !(conf.getAttr('appName') in ['base-dingding-api-gateway','base-dingding-auth-service','base-dingding-message-service','base-dingding-tuoke-live-classroom','base-dingding-frontend','base-dingding-content-security']) }
                                    expression { return conf.getAttr('deployEnvStatus') == 'stop'}
                                    expression { return conf.getAttr('deployEnv') == 'not-deploy' }
                                    expression { return conf.getAttr('codeLanguage') != 'android'}
                                    expression { return conf.getAttr('codeLanguage') != 'unity' }
                                }
                            }
                            
                        }
                        steps {
                            container('adp') {
                                script {
                                    conf.setAttr('buildImageTag', conf.getBuildImageAddressTag())
                                    conf.setAttr('buildImageAddress', conf.getBuildImageAddress('docker.dm-ai.cn'))
                                    conf.printAppConf()
                                    makeDockerImage.makeDockerComposeYml()
                                }
                            }
                        }
                    }
                }
            }

            stage('构建') {
                parallel {
                    // unity需要 TODO 整合android加固流程
                    stage('安卓app加固') {
                        when {
                            anyOf {
                                expression { return conf.getAttr('codeLanguage') == 'unity' };
                            }
                        }

                        steps {
                            container('jiagu') {
                                script {
                                    try {
                                        sh 'sh -x /opt/jiagu.sh'
                                    } catch (e) {
                                        sh 'echo ${e}'
                                        conf.failMsg = '编译失败！'
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                    stage('镜像制作、镜像上传') {
                        when {
                            allOf {
                                expression { return conf.ifBuild() };
                                expression { return conf.getAttr('codeLanguage') != 'android'};
                                expression { return conf.getAttr('codeLanguage') != 'unity' };
                            }
                        }
                        steps {
                            container('adp') {
                                script {
                                    if (conf.ifMakeImage() && conf.getAttr('makeImage')) {
                                        // try {
                                        //     withEnv(conf.withEnvList) {
                                        //         sh 'dockerize -template nginx.conf:nginx.conf || echo 0'
                                        //     }
                                        // } catch (e) {
                                        //     sh 'echo ${e}'
                                        // }

                                        try {
                                            makeDockerImage.makeImage()
                                        } catch (e) {
                                            sh 'echo ${e}'
                                            conf.failMsg = '制作容器镜像失败！'
                                            throw e
                                        }

                                        try {
                                            makeDockerImage.pushImage()
                                        } catch (e) {
                                            sh 'echo ${e}'
                                            conf.failMsg = '推送镜像到镜像仓库失败！'
                                            throw e
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            stage('部署') {
                when {
                    allOf {
                        expression { return conf.getAttr('deploy') }
                        expression { return conf.getAttr('codeLanguage') != 'android'}
                        expression { return conf.getAttr('codeLanguage') != 'unity' }
                        expression { return conf.getAttr('deployEnv') != 'not-deploy' }
                    }
                }
                steps {
                    container('adp') {
                        script {
                            withEnv(conf.withEnvList) {
                                sh 'echo 部署的环境是 $BUILD_ENV_deployEnv'
                            }
                            if (conf.getAttr('buildPlatform') == 'adp')  {
                                // adp 自动生成模板
                                try {
                                    withEnv(conf.withEnvList) {
                                        sh 'printenv'
                                        sh 'cd /workspace; dockerize -template src_dir:dest_dir  && cat dest_dir/template.tmpl'
                                    }
                                } catch (e) {
                                    sh "echo ${e}"
                                }

                            }
                            // 发布到测试环境的条件
                            boolean isTest = conf.getAttr('deployEnv') == 'test'
                            // 其它非测试环境的发布条件  条件不能换行
                            boolean isNotTest = !isTest && conf.getAttr('deployEnv') != 'not-deploy' && conf.getAttr('deployEnvStatus') != 'stop'

                            if (isNotTest) {
                                if (conf.getAttr('deployEnv') == 'prd' && deployMasterPassword != 'dmai2019999') {
                                    throw 'master分支请运维人员触发！'
                                }
                            }

                            boolean isCheckService = false

                            try {
                                sh String.format("mkdir -p ~/.kube && wget http://adp-api.dm-ai.cn/api/v1/get-k8s-key-file?env='%s' -O ~/.kube/config", conf.getAttr('deployEnv'))
                                if (conf.getAttr('ifUseIstio')) {
                                    sh String.format('kubectl label ns %s istio-injection=enabled --overwrite', conf.getAttr('namespace'))
                                }

                                if (conf.getAttr('buildPlatform') != 'adp' || conf.getAttr('customKubernetesDeployTemplate')) {
                                    deploykubernetes.createIngress()

                                    if (isTest) {
                                        deploykubernetes.createConfigMap(true)
                                    } else if (isNotTest) {
                                        deploykubernetes.createConfigMap(false)
                                    }

                                    deploykubernetes.deployKubernetes()
                                } else {
                                    if (isTest) {
                                        deploykubernetes.createConfigMap(true)
                                    } else if (isNotTest) {
                                        deploykubernetes.createConfigMap(false)
                                    }

                                    deploykubernetes.deleteOldIngress()
                                    sh 'kubectl apply -f /workspace/dest_dir/template.tmpl'
                                }

                                isCheckService = true
                            } catch (e) {
                                sh "echo ${e}"
                                conf.failMsg = '使用kubectl部署服务到k8s失败！'
                                throw e
                            }

                            // 服务检查 条件不能换行
                            isCheckService = isCheckService && conf.getAttr('deployEnv') != 'not-deploy' && conf.getAttr('checkPodsStatus') && conf.getAttr('deployEnvStatus') != 'stop'

                            if (isCheckService) {
                                sh 'echo 检查pod是否正常运行，等待限时1200秒'
                                sh 'sleep 10'
                                try {
                                    kubernetesStatusCheck.waitKubernetesServerStartedV1()
                                } catch (e) {
                                    sh 'echo ${e}'
                                    conf.failMsg = e
                                    throw e
                                }

                                if (conf.getAttr('deployRes') == 'ok') {
                                    sh 'echo 部署在k8s集群中的服务已正常运行'
                                } else {
                                    conf.failMsg = conf.getAttr('deployMsg')
                                    throw conf.getAttr('deployMsg')
                                }
                            }
                        }
                    }
                }
            }
            stage('后处理') {
                steps {
                    echo '同步构建结果到数据库、发送邮件给用户'
                }
            }
        }

        post {
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

            always {
                script {
                    echo currentBuild.result
                    dmaiEmail.writeBuildResultToAdp(currentBuild.result)
                    dmaiEmail.writeBuildResultToAdpResult(currentBuild.result)
                }
            }
        }
    }
}
