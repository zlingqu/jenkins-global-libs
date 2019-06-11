package com.dmai

class Conf implements Serializable{

    public String appName
    public String dockerRegistryHost
    private Map<String, String> userSetMap
    private Map<String, String> appConf
    private Map<String, String> jenkinsEnv

    Conf(String appName, Map<String, String> userSetMap) {
        this.appName = appName
        this.dockerRegistryHost = 'docker.dm-ai.cn'
        this.userSetMap = userSetMap
        this.appConf = new GlobalConfig().globalConfig.get(appName)
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
