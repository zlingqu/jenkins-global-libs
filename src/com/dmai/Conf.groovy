package com.dmai

class Conf implements Serializable{

    public String appName
    public String dockerRegistryHost
    public String jenkinsAddress
    public String vueAppScene
    public String vueAppSchool
    public String modelVersion
    public String kubernetesStatusCheckHttpAddress
    public int checkKubernetesServiceStatusSleepTimes
    protected final def script
    private Map<String, String> userSetMap
    private Map<String, String> appConf
    private Map<String, Map<String, String>> globalConfig
    private Map<String, String> jenkinsEnv

    Conf(script, String appName, Map<String, String> userSetMap) {
        this.script = script
        this.appName = appName
        this.dockerRegistryHost = 'docker.dm-ai.cn'
        this.jenkinsAddress = 'http://jenkins.ops.dm-ai.cn'
        this.kubernetesStatusCheckHttpAddress = 'http://service-k8s-app-status-check.dm-ai.cn/api/v1/app_status_check'
        this.checkKubernetesServiceStatusSleepTimes = 120 // 120 SECONDS
        this.userSetMap = userSetMap
        this.vueAppScene = ''
        this.vueAppSchool = ''
        this.modelVersion = ''

        // 全局设置中没添加这个项目，需要报错。
        try {
            this.globalConfig = new GlobalConfig().globalConfig
            if (this.globalConfig.containsKey(appName)) {
                this.appConf = this.globalConfig.get(appName)
            } else {
                this.appConf = new HashMap<String, String>()
            }

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

    public def setAttr(key, value) {
        this.appConf.put(key, value)
    }

    public def setVueAppScene(String vueAppScene) {
        this.vueAppScene = vueAppScene
    }

    public def setAppName(String appName) {
        if (this.vueAppScene != 'main') {
            this.appName = appName.toLowerCase()
        }
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
        String printString = ''
        Set<String> key = this.appConf.keySet()
        for (Iterator<String> it = key.iterator(); it.hasNext();){
            String s = it.next();
            printString += s + " : " + this.appConf.get(s) + "\n"
//            this.script.sh "echo ${s} : ${this.appConf.get(s)}"
        }
        this.script.sh "echo "
//        for (i in this.appConf) {
//            this.script.sh "echo ${this.appConf.get(i)}"
//        }
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
        if (this.appName in  [ 'xmc-online-api', 'xmc-detection-api', 'xmc-gesture-api', 'xmc-holdobj-api', 'facial-expression-cls' ]) return 'test'
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
}
