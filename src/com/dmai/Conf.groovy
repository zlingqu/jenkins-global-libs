package com.dmai

class Conf implements Serializable{

    public String appName
    public String dockerRegistryHost
    public String jenkinsAddress
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

        // 全局设置中没添加这个项目，需要报错。
        try {
            this.appConf = new GlobalConfig().globalConfig.get(appName)
        }
        catch (e) {
            this.script.sh "echo ${e}"
        }
    }

    // get attr
    public def getAttr(String attrName) {
        return this.appConf.get(attrName)
    }

    // set user attr
    public def setUserAttr(Map<String, String> userSetMap) {
        this.appConf.putAll(userSetMap)
    }

    public def setJenkinsAttrToConf(env) {
        jenkinsEnv = [:]
        jenkinsEnv.put('jobName', env.JOB_NAME.split("/")[0])
        jenkinsEnv.put('branchName', env.BRANCH_NAME)
        jenkinsEnv.put('buildNumber', env.BUILD_NUMBER)
        this.appConf.putAll(jenkinsEnv)
    }
}
