package com.dmai
import java.util.concurrent.TimeUnit
import groovy.json.JsonSlurper

class KubernetesStatusCheck {

    private Conf conf
    protected final def script

    KubernetesStatusCheck(script, Conf conf) {
        this.script = script
        this.conf = conf
    }

    private String requestBodyString() {
        return String.format('''
{ 
"kubernetes_env": "%s", 
"kubernetes_pods_name": "%s", 
"namespace": "%s"
}
''', this.conf.getAttr('deployEnv'), this.conf.appName, this.conf.getAttr("namespace"))
    }

    private String getServiceAppStatus() {
        URL url = new URL(this.conf.kubernetesStatusCheckHttpAddress)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()
        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.doOutput = true
        def writer = new OutputStreamWriter(conn.outputStream)
        writer.write(this.requestBodyString())
        writer.flush()
        writer.close()
        conn.connect()
        def respText = conn.content.text
        return respText
    }

    private String getServiceAppStatusV1Url() {
        return String.format('''http://service-k8s-app-status-check-v1.devops.dev.dm-ai.cn/api/v1/pods-status?env=%s&namespace=%s&appName=%s''', this.conf.getAttr('deployEnv'), this.conf.getAttr("namespace"), this.conf.getAttr("jobName"))
    }

    private Map getServiceAppStatusV1() {
        URL url = new URL(this.getServiceAppStatusV1Url())
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()
        conn.setRequestMethod("GET")
        conn.connect()
        def respText = conn.content.text
        conn.disconnect()
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(respText)
        assert object instanceof Map
        return object
    }

    public void waitKubernetesServerStartedV1() {
        int count = 0
        while ( count <= 1200 ) {
            def deployInfo = this.getServiceAppStatusV1()
            if (deployInfo.res == "fail" || (deployInfo.res == "ok" && deployInfo.status == "ok")) {
                this.conf.setAttr('deployRes', deployInfo.res)
                this.conf.setAttr('deployMsg', deployInfo.msg)
                break
            } else if ( deployInfo.res == "ok" && deployInfo.status == "continue"){
                println(deployInfo.msg)
                count += 3
                TimeUnit.SECONDS.sleep(3)
            }
        }
    }

    public boolean waitKubernetesServerStarted() {
        int count = 0
        while (count <= this.conf.checkKubernetesServiceStatusSleepTimes) {
            if (this.getServiceAppStatus() == "true") {
                return true
            } else {
                count += 5
                TimeUnit.SECONDS.sleep(5)
            }
        }
        return false
    }
}
