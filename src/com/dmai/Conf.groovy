package com.dmai

class Conf implements Serializable{

    private Map<String, Map<String, String>> conf
    private Map<String, String> userSetMap
    private String appName

    Conf(String appName, Map<String, String> userSetMap) {
        this.appName = appName
        this.userSetMap = userSetMap
        this.conf = new GlobalConfig().globalConfig
        this.setUserAttr(userSetMap)
    }

    // get attr
    public GetAttr(String attrName) {
        return this.conf.get(this.appName).get(attrName)
    }

    // set user attr
    private setUserAttr(Map<String, String> userMap) {
        this.conf.get(this.appName).putAll(userMap)
    }
}
