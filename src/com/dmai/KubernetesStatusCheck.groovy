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

    private String getImageSha256() {
        // http://127.0.0.1/api/v1/docker-image-sha256?space=devops&project=service-adp-env&tag=dev-16-70f73be74615f43f9ea8718d7a774faf6c0fd638
        def queryUrl = String.format('''http://adp-api.dm-ai.cn/api/v1/docker-image-sha256?space=%s&project=%s&tag=%s''',
                this.conf.getAttr("namespace"),
                this.conf.getAttr("jobName"),
                this.conf.getAttr('buildImageTag')
        )

        URL url = new URL(queryUrl)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()
        conn.setRequestMethod("GET")
        conn.connect()
        def respText = conn.content.text
        conn.disconnect()
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(respText)
        assert object instanceof Map
        println(11111111111111111111111111111)
        println(queryUrl)
        println(object)
        return object.data
    }

    private String getServiceAppStatusV1Url(String imageSha) {

//        def gitString = this.conf.getAttr('versionControlMode') == 'GitCommitId' ? this.conf.getAttr('gitVersion') : this.conf.getAttr('gitTag')

        return String.format('''http://adp-api.dm-ai.cn/api/v1/pods-status?env=%s&namespace=%s&appName=%s&imageSha=%s''',
                this.conf.getAttr('deployEnv'),
                this.conf.getAttr("namespace"),
                this.conf.getAttr("jobName"),
                imageSha)
    }

    private Map getServiceAppStatusV1(String imageSha) {
        URL url = new URL(this.getServiceAppStatusV1Url(imageSha))
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
        int sha2 = 0
        int searchErr = 0
        def imageSha = ""

        // 查询 sha
        while (sha2 <= 3) {
            try {
                imageSha = this.getImageSha256()
                if (imageSha != "") {
                    break
                }
                if (imageSha == "") {
                    sha2 += 1
                }

            } catch (e) {
                sha2 += 1
                continue
            }
        }

        //
        if (imageSha == "") {
            def msg = "构建错误，构建过程中，查询镜像的sha值失败，此sha值用来检查部署后的服务"
            this.conf.setAttr('deployRes', msg)
            this.conf.setAttr('deployMsg', msg)

            return
        }


        while (count <= 1800) {
            try {
                def deployInfo = this.getServiceAppStatusV1(imageSha)
                if (deployInfo.res == "fail" || (deployInfo.res == "ok" && deployInfo.status == "ok")) {
                    this.conf.setAttr('deployRes', deployInfo.res)
                    this.conf.setAttr('deployMsg', deployInfo.msg)
                    return
                } else if (deployInfo.res == "ok" && deployInfo.status == "continue") {
                    println(deployInfo.msg)
                    count += 3
                    TimeUnit.SECONDS.sleep(3)
                }
            } catch (e) {
                searchErr += 1
                if (searchErr >= 6) {
                    this.conf.setAttr('deployRes', '1800秒内查询k8s的pod的状态，,查询过程中查询程序异常6次，失败, 此为构建网络问题，请手动检查pod状态')
                    this.conf.setAttr('deployMsg', '1800秒内查询k8s的pod的状态，查询过程中查询程序异常6次，失败，此为构建网络问题，请手动检查pod状态')
                    return
                }
                continue
            }
        }

        this.conf.setAttr('deployRes', '1800秒内服务未起来，异常，请手动检查日志和服务状态')
        this.conf.setAttr('deployMsg', '1800秒内服务未起来，异常，请手动检查日志和服务状态')

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
