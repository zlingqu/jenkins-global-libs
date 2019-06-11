package com.dmai

class Conf implements Serializable{

    public String appName
    private Map<String, String> userSetMap
    private Map<String, String> appConf

    Conf(String appName, Map<String, String> userSetMap) {
        this.appName = appName
        this.userSetMap = userSetMap
        this.appConf = new GlobalConfig().globalConfig.get(appName)
//        userSetMap.each {k, v ->
//            System.out.println(k)
//        }
//        this.SetUserAttr(userSetMap)
    }

    // get attr
    def getAttr(String attrName) {
        return this.appConf.get(attrName)
    }

    // set user attr
    def setUserAttr(Map<String, String> userSetMap) {
        this.appConf.putAll(userSetMap)
//        userSetMap.each {k, v ->
//            this.appConf.pu
//            System.out.println(k)
//        }
    }
}
