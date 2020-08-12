package com.dmai

class Conf implements Serializable {

    public String appName
    public String dockerRegistryHost
    public String jenkinsAddress
    public String vueAppScene
    public String vueAppSchool
    public String modelVersion
    public String kubernetesStatusCheckHttpAddress
    public String failMsg
    public int checkKubernetesServiceStatusSleepTimes
    protected final def script
    private Map<String, String> userSetMap
    private Map<String, String> appConf
    private Map<String, Map<String, String>> globalConfig
    private Map<String, String> jenkinsEnv
    public withEnvList
    public List<String> privateK8sEnv

    Conf(script, String appName, Map<String, String> userSetMap) {
        this.script = script
        this.appName = appName
        this.dockerRegistryHost = 'docker.dm-ai.cn'
        this.jenkinsAddress = 'http://jenkins.ops.dm-ai.cn'
        this.kubernetesStatusCheckHttpAddress = 'http://service-k8s-app-status-check.dm-ai.cn/api/v1/app_status_check'
        this.checkKubernetesServiceStatusSleepTimes = 180 // 120 SECONDS
        this.userSetMap = userSetMap
        this.vueAppScene = ''
        this.vueAppSchool = ''
        this.modelVersion = ''
        this.failMsg = ''
        this.withEnvList = []
        this.privateK8sEnv = ['lexue', 'tuoke']

        // 全局设置中没添加这个项目，需要报错。
        try {
            this.globalConfig = new GlobalConfig().globalConfig
            this.appConf = this.globalConfig.containsKey(appName) ? this.globalConfig.get(appName) : new HashMap<String, String>()
            // set servicePort
            this.appConf.put('servicePort', '80')
        }
        catch (e) {
            //
            this.script.sh "echo ${e}"
        }
    }

    public def setockerRegistryHost(String dockerRegistryHost) {
        this.dockerRegistryHost = dockerRegistryHost
    }

    public def setReplicas(String replicas) {
        this.appConf.put('replicas', replicas)
    }

    public def jenkinsWorkPath() {
        return String.format('''/home/jenkins/workspace/%s_%s''', this.getAttr('jobName'), this.getAttr('branchName'))
    }

    public def getBuildImageAddressTag() {
        if (this.getAttr('versionControlMode') == 'GitTags') {
            return String.format('''tag-%s''',
                    this.getAttr('gitTag')) + "-" + this.getAttr('deployEnv')
        }
        return String.format('''%s-%s-%s''',
                this.getAttr('branchName'),
                this.getAttr('buildNumber'),
                this.getAttr('gitVersion')) + "-" + this.getAttr('deployEnv')
    }

    public def getBuildImageAddress() {
        return String.format('''%s/%s/%s:''',
                this.dockerRegistryHost,
                this.getAttr('namespace'),
                this.appName
        ) + getAttr('buildImageTag')
//        if (this.getAttr('versionControlMode') == 'GitTags') {
//            return String.format('''%s/%s/%s:tag-%s''',
//                    this.dockerRegistryHost ,
//                    this.getAttr('namespace'),
//                    this.appName, this.getAttr('gitTag'))
//        }
//       return String.format('''%s/%s/%s:%s-%s-%s''',
//               this.dockerRegistryHost ,
//               this.getAttr('namespace'),
//               this.appName,
//               this.getAttr('branchName'),
//               this.getAttr('buildNumber'),
//               this.getAttr('gitVersion'))
    }

    public def getK8sWebAddress() {
        def k8sWebAddress = String.format('''http://%s.k8s.dm-ai.cn/#/service/%s/%s?namespace=%s''', this.getAttr('deployEnv'), this.getAttr('namespace'), this.getAttr('jobName'), this.getAttr('namespace'))
        if (this.getAttr('deployEnv') == 'prd') {
            return k8sWebAddress.replaceAll('http://prd.', 'http://')
        }
        return k8sWebAddress
    }

    public def setAttr(key, value) {
        this.appConf.put(key, value)
    }

    public def setVueAppScene(String vueAppScene) {
        this.vueAppScene = vueAppScene
    }

    public def setAppName(String appName) {
//        if (this.vueAppScene != 'school') {
        this.appName = appName.toLowerCase()
//        }
    }

    public def setVueAppSchool(String vueAppSchool) {
        this.vueAppSchool = vueAppSchool
    }

    public def setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion
    }

    // get attr
    public def getAttr(String attrName) {
        try {
            return this.appConf.get(attrName)
        } catch (e) {
            return null
        }
    }

    public def getDomain() {
        String cfgDomain = this.getAttr('domain')
        String userRequestAddress = ''
        String deployEnv = this.getAttr('deployEnv')
        if (cfgDomain) {
            if (cfgDomain.indexOf('deploy-env') != -1) {
                if (this.getAttr('deployEnv') == 'prd') {
                    userRequestAddress = cfgDomain.replaceAll('deploy-env.', '')
                } else {
                    userRequestAddress = cfgDomain.replaceAll('deploy-env', this.getAttr('namespace') + '.' + deployEnv)
                }
            } else {
                if (this.getAttr('deployEnv') == 'prd') {
                    userRequestAddress = cfgDomain
                } else {
//                    userRequestAddress = deployEnv + '.' + cfgDomain
                    userRequestAddress = cfgDomain.replaceAll('dm-ai.cn', this.getAttr('namespace') + '.' + deployEnv + '.' + 'dm-ai.cn')
                }
            }
        }
        return userRequestAddress
    }

    public String getAppUrl() {
        switch (this.getAttr('svcType')) {
            case 'ClusterIP':
                return '用户使用的svc模式为ClusterIP,外部无法直接访问。'
            case 'NodePort':
                return this.nodePortAddress() + ':' + this.getAttr('nodePort')
        }
    }

    private String nodePortAddress() {
        switch (this.getAttr('branchName')) {
            case 'master':
                return 'http://192.168.11.20'
            case 'dev':
                return this.getDevUrl()
        }
    }

//    根据dev标签来判断用户的dev分支部署在那个环境
    private String getDevUrl() {
        switch (this.getAttr('deployEnv')) {
            case 'test':
                return 'http://192.168.3.140'
            case 'dev':
                return 'http://192.168.3.21'
            case 'master':
                return 'http://192.168.11.20'
            case 'jenkins':
                return 'http://192.168.69.32'
        }
    }

    // print appConf
    public def printAppConf() {
        this.withEnvList = []
        String printString = ''
        Set<String> key = this.appConf.keySet()
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            String s = it.next();
            printString += s + " : " + this.appConf.get(s) + "\n"
            this.withEnvList += ['BUILD_ENV_' + s + '=' + this.appConf.get(s)]
//            this.script.sh "echo ${s} : ${this.appConf.get(s)}"
        }
        return printString
//        for (i in this.appConf) {
//            this.script.sh "echo ${this.appConf.get(i)}"
//        }
    }

    // set user attr
    public def setUserAttr(Map<String, String> userSetMap) {
        println(this.appConf)
        println(userSetMap)
        this.appConf.putAll(userSetMap)
    }

    public def setJenkinsAttrToConf(env, currentBuild) {
        jenkinsEnv = [:]
        jenkinsEnv.put('jobName', env.JOB_NAME.split("/")[0])
//        jenkinsEnv.put('branchName', env.BRANCH_NAME)
        jenkinsEnv.put('branchName', currentBuild.projectName)
//        jenkinsEnv.put('branchName', 'master')
        jenkinsEnv.put('buildNumber', env.BUILD_NUMBER)
        this.appConf.putAll(jenkinsEnv)
    }

    public def getDeployEnv() {
        if (this.appName in ['xmc-online-api', 'xmc-body-action', 'xmc-detection-api', 'xmc-gesture-api', 'xmc-holdobj-api', 'facial-expression-cls']) return 'test'
        def branchName = this.getAttr('branchName')
        switch (branchName) {
            case 'master':
                return 'prd'
            case 'dev':
                return this.getAttr('dev')
            case 'stage':
                return "stage"
            case 'release':
                return "test"
            default:
                return "default"
        }
    }

    public def ifBuild() {
        if (this.getAttr('gitVersion') == 'update') return false
        if (this.getAttr('deployPassword') != 'dmai2019999' && this.getAttr('deployEnv') == 'prd') return false
        if (this.getAttr('namespace') == 'test') return false
        if (this.getAttr('deployEnv') == 'default') return false
//        if (this.getAttr('buildPlatform') == 'jenkins' &&  !(this.getAttr('branchName') in ['master', 'dev', 'stage','release']))
        return true
//        if (this.getAttr('gitVersion') != 'update' &&
//                (this.getAttr('deployPassword') != 'dmai2019999' || this.getAttr('deployEnv') == 'prd')
//        ) {
//            return true
//        }
//        return false
    }

    public def ifMakeImage() {
        if (this.getAttr('codeLanguage') in ['android', 'unity']) {
            return false
        }
        return true
    }
}
