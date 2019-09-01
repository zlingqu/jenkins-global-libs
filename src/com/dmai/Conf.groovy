package com.dmai

class Conf implements Serializable{

    public String appName
    public String dockerRegistryHost
    public String jenkinsAddress
    public String vueAppScene
    public String vueAppSchool
    public String modelVersion
    protected final def script
    private Map<String, String> userSetMap
    private Map<String, String> appConf
    private Map<String, String> jenkinsEnv

    Conf(script, String appName, Map<String, String> userSetMap) {
        this.script = script
        this.appName = appName
        this.dockerRegistryHost = 'docker.dm-ai.cn'
        this.jenkinsAddress = 'http://jenkins.ops.dm-ai.cn'
        this.userSetMap = userSetMap
        this.vueAppScene = ''
        this.vueAppSchool = ''
        this.modelVersion = ''

        // 全局设置中没添加这个项目，需要报错。
        try {
            this.appConf = new GlobalConfig().globalConfig.get(appName)
        }
        catch (e) {
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

    // set user attr
    public def setUserAttr(Map<String, String> userSetMap) {
        this.appConf.putAll(userSetMap)
    }

    public def setJenkinsAttrToConf(env, currentBuild) {
        jenkinsEnv = [:]
        jenkinsEnv.put('jobName', env.JOB_NAME.split("/")[0])
//        jenkinsEnv.put('branchName', env.BRANCH_NAME)
        jenkinsEnv.put('branchName', currentBuild.projectName)
        this.script.sh "1111111111"
        jenkinsEnv.put('buildNumber', env.BUILD_NUMBER)
        this.appConf.putAll(jenkinsEnv)
    }
}
