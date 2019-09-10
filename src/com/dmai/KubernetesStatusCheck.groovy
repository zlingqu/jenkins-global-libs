package com.dmai
import java.util.concurrent.TimeUnit

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
''', this.conf.getDeployEnv(), this.conf.appName, this.conf.getAttr("namespace"))
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

    public boolean waitKubernetesServerStarted() {
        int count = 0
        while (count <= this.conf.checkKubernetesServiceStatusSleepTimes) {
            TimeUnit.SECONDS.sleep(10)
            if (this.getServiceAppStatus() == "true") {
                return true
            } else {
                count += 10
            }
        }
        return false
    }
}
