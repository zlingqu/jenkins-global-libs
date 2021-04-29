package com.dmai

// 全局配置，包括配置加载、特殊项目配置处理等

class Conf implements Serializable {

    public String appName
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
    private Map<String, String> jenkinsEnv
    public withEnvList

    Conf(script, String appName, Map<String, String> userSetMap) {
        this.script = script
        this.appName = appName
        this.jenkinsAddress = 'http://jenkins.ops.dm-ai.cn'
        this.kubernetesStatusCheckHttpAddress = 'http://adp-api.dm-ai.cn/api/v1/app_status_check'
        this.checkKubernetesServiceStatusSleepTimes = 180 // 120 SECONDS
        this.userSetMap = userSetMap
        this.vueAppScene = ''
        this.vueAppSchool = ''
        this.modelVersion = ''
        this.failMsg = ''
        this.withEnvList = []
        this.appConf =  new HashMap<String, String>()
   
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

    public def getBuildImageAddress( docker_registry_host) {
        return String.format('''%s/%s/%s:''',
                docker_registry_host,
                this.getAttr('namespace'),
                this.appName
            ) + getAttr('buildImageTag')
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


    // print appConf
    public def printAppConf() {
        this.withEnvList = []
        String printString = ''
        Set<String> key = this.appConf.keySet()
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            String s = it.next();
            printString += s + " : " + this.appConf.get(s) + "\n"
            this.withEnvList += ['BUILD_ENV_' + s + '=' + this.appConf.get(s)]
        }
        return printString

    }

    // set user attr
    public def setUserAttr(Map<String, String> userSetMap) {
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
    }

    public def ifMakeImage() {
        if (this.getAttr('codeLanguage') in ['android', 'unity']) {
            return false
        }
        return true
    }
}
