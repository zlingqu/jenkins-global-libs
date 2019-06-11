package com.dmai

class Conf implements Serializable{

    private Map<String, Map<String, String>> conf
    private Map<String, String> userSetMap

    // init
    Conf(String appName, Map<String, String> userSetMap, Map<String, String> jenkinsEnv) {
//        this.conf = new GlobalConfig().globalConfig
        this.userSetMap = userSetMap
//        this.setUserAttr(appName, userSetMap, jenkinsEnv)
    }

    // get attr
    public GetAttr(String appName, String attrName) {
        return this.conf.get(appName).get(attrName)
    }

    // set user attr
    private setUserAttr(String appName, Map<String, String> userMap, Map<String, String> jenkinsEnv) {
        this.conf.get(appName).putAll(userMap)
        this.conf.get(appName).putAll(jenkinsEnv)
    }
}
