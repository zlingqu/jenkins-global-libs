package com.dmai

class Conf implements Serializable{

    private String appName
    private Map<String, String> userSetMap
    private Map<String, String> appConf

    Conf(String appName, Map<String, String> userSetMap) {
        this.appName = appName
        this.userSetMap = userSetMap
        this.appConf = new GlobalConfig().globalConfig.get(appName)
        this.setUserAttr(userSetMap)
    }

    // get attr
    public GetAttr(String attrName) {
        return this.appConf.get(attrName)
    }

    // set user attr
    void setUserAttr(Map<String, String> userMap) {
//        this.appConf.putAll(userMap)
        userSetMap.each {k, v ->
            println(k, v)
        }
    }
}
