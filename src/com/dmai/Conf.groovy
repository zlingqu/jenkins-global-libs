package com.dmai

class Conf implements Serializable{

    private String appName
    private Map<String, String> userSetMap
    private Map<String, Map<String, String>> conf

    Conf(String appName, Map<String, String> userSetMap) {
        this.appName = appName
        this.userSetMap = userSetMap
        this.conf = new GlobalConfig().globalConfig
//        this.setUserAttr(userSetMap)
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
