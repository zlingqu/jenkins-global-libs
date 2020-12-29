package com.dmai

import com.tool.Tools
import java.net.URLEncoder
import java.util.concurrent.TimeUnit
// import java.io.File

class DmaiEmail {

    protected final def script
    private Conf conf
    private String adpUrl
    private String adpResultUrl
    private String jenkinsUrl
    private String adpUrlApp

    DmaiEmail(script, Conf conf) {
        this.script = script
        this.conf = conf
        this.adpUrl = 'http://adp.dm-ai.cn/api/v1/deployments/change'
        this.adpResultUrl = 'http://adp.dm-ai.cn/api/v1/result'
        this.jenkinsUrl = 'http://jenkins.ops.dm-ai.cn'
        this.adpUrlApp = 'http://adp.dm-ai.cn/#/deployment-management'
    }

    public userSureEmail() {
        try {
            this.script.emailext(
                    body: String.format('http://jenkins.ops.dm-ai.cn/job/%s/job/%s/%s/input/',
                            this.conf.getAttr('jobName'),
                            this.conf.getAttr('branchName'),
                            this.conf.getAttr('buildNumber')
                    ),
                    subject: '紧急->用户确认邮件',
                    to: conf.getAttr('emailAddress')
            )
        }
        catch (e) {
            this.script.sh "${e}"
        }
    }

    private String requestBodyString(String token, String status) {
        return String.format('''
                {
                "token":"%s",
                "status":"%s"
                }
                ''', token, status)
    }

    private String reqResultString() {
        return String.format('''
                {
                "name": "%s",
                "deploy_env": "%s",
                "version": "%s"
                }
                ''', this.conf.getAttr('jobName'), this.conf.getAttr('deployEnv'), this.conf.getAttr('jsVersion'))
    }

    public writeBuildResultToAdp(String buildResult) {
        if (buildResult == 'SUCCESS') {
            this.conf.setAttr('buildResult', 'success')
        }

        // 测试等待5秒后发送是否可以取到真实的值。
        TimeUnit.SECONDS.sleep(5)

        def jenkinsUrl = String.format('''%s/blue/organizations/jenkins/%s/detail/%s/%s/pipeline''', this.jenkinsUrl, this.conf.getAttr('jobName'), URLEncoder.encode(this.conf.getAttr('jenkinsBranchName'), 'UTF-8'), this.conf.getAttr('buildNumber'))
        def status =  this.conf.getAttr('buildResult') == 'success' ? 'online' : 'failed'

        this.script.sh "echo 发送构建结果 ${this.adpUrl}"

        URL url = new URL(this.adpUrl)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()
        conn.setRequestMethod('POST')
        conn.setRequestProperty('Content-Type', 'application/json')
        conn.doOutput = true
        def writer = new OutputStreamWriter(conn.outputStream)
        writer.write(this.requestBodyString(jenkinsUrl, status))
        writer.flush()
        writer.close()
        conn.connect()
        def respText = conn.content.text
        println(respText)
        return respText
    }

    public writeBuildResultToAdpResult(String buildResult) {
        if (buildResult == 'SUCCESS') {
            this.conf.setAttr('buildResult', 'success')
        }

        if (this.conf.ifBuild()) {
            this.script.sh "echo 发送构建结果 ${this.adpResultUrl}"
            URL url = new URL(this.adpResultUrl)
            HttpURLConnection conn = (HttpURLConnection) url.openConnection()
            conn.setRequestMethod('POST')
            conn.setRequestProperty('Content-Type', 'application/json')
            conn.doOutput = true
            def writer = new OutputStreamWriter(conn.outputStream)
            writer.write(this.reqResultString())
            writer.flush()
            writer.close()
            conn.connect()
            def respText = conn.content.text
            println(respText)
            return respText
        }
    }

    public sendEmail(String buildResult) {
        if (!this.conf.ifBuild()) {
            return
        }

        // 构建结果的中文提示：
        def buildResultZh = buildResult == 'success' ? '成功' : '失败: ' + conf.failMsg
        try {
            this.script.emailext(
                    body: this.emailBody(buildResultZh),
                    subject: this.conf.appName + ',构建 : ' + buildResultZh + '，部署环境：' + this.conf.getAttr('deployEnv'),
                    to: conf.getAttr('emailAddress') + ',quzhongling@dm-ai.cn,liaolonglong@dm-ai.cn'
            )
        }
        catch (e) {
            this.script.sh "${e}"
        }
    }

    private String emailBody(String buildResult) {
        def String apkViewUrl = ''
        // def String apkViewUrlQrcode = ''
        apkViewUrl = String.format('''http://192.168.69.32:8888/files/view/android_home/%s/%s/%s/%s-build%s-%s.apk''',
            this.conf.appName,
            this.conf.getAttr('deployEnv'),
            new Date().format('yyyyMMdd'),
            this.conf.appName,
            this.conf.getAttr('buildNumber'),
            this.conf.getAttr('gitVersion')
        )

        // def apkViewUrlQrcode = this.script.sh String.format("curl -s ci-test.devops.dev.dm-ai.cn/qrcode?url=$s", apkViewUrl)
        // def apkViewUrlQrcode = sh ( script: String.format('''curl -s ci-test.devops.dev.dm-ai.cn/qrcode?url=$s''', apkViewUrl), returnStdout: true).trim()
        // shellCommand = String.format("curl -s ci-test.devops.dev.dm-ai.cn/qrcode?url=$s|base64 > apkViewUrlQrcode.txt", apkViewUrl)
        // shellCommand.execute()

        if (conf.getAttr('codeLanguage') == 'android') {
            // this.script.sh String.format('''curl -s ci-test.devops.dev.dm-ai.cn/qrcode?url=$s|base64 > apkViewUrlQrcode.txt''', apkViewUrl)
            // this.script.sh String.format('curl -s ci-test.devops.dev.dm-ai.cn/qrcode?url="$s"|base64 \> apkViewUrlQrcode.txt', apkViewUrl)
            this.script.sh sprintf('curl -s http://ci-test.devops.dev.dm-ai.cn/qrcode?url=%s | base64 > apkViewUrlQrcode.txt', apkViewUrl)
            apkViewUrlQrcode = readFile('apkViewUrlQrcode.txt').trim()
        }
        // def apkViewUrlQrcode = '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'
        def textComman = '''
            <html>
            <head>
                <meta charset="utf-8">
                <!-- <meta name="viewport" content="width=device-width,initial-scale=1.0"> -->
                <!-- <title>y</title> -->
                <style type="text/css">
                    table.dataintable {
                        margin-top: 15px;
                        border-collapse: collapse;
                        border: 1px solid #aaa;
                        /* width: 100%; */
                        width: 1000px;
                    }
                    tr > td {
                        background: #f4f5f7;
                        height: 35px;
                        padding-left: 10px;
                        padding-right: 10px;
                        padding-top: 7px;
                        padding-bottom: 7px;
                        font-size: 16px;
                    }
                    tr > th {
                        height: 35px;
                        padding-left: 10px;
                        padding-right: 10px;
                        padding-top: 7px;
                        padding-bottom: 7px;
                        text-align: left;
                        font-size: 18px;
                    }
                </style>
            </head>
            <body>
                <h2 style="font-size: 22px; font-weight:bold;">构建结果如下：</h2>
                <table class="dataintable" border="1">
                    <tbody >
                        <tr style="background:#f7f5f4">
                            <th style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">名称</th>
                            <th style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">信息</th>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">构建项目</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">$appName</td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">k8s外部访问地址</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a target="_blank" href="$buildEnvInfo">$buildEnvInfo</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">k8s内部访问地址</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">http://$appName.$namespace</td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">Jenkins-构建地址(blue)</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a target="_blank" href="$jenkinsAddress/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline">Jenkins-blue-url</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">Jenkins-构建地址(old)</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a target="_blank" href="$jenkinsAddress/job/$jobName/job/$branchName">Jenkins-old-url</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">代码Git地址</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="$gitAddress">$gitAddress</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">代码Git分支</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">$branchName</td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">K8s Dashboard查看</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="$k8sWebAddress">$k8sWebAddress</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">sonar检查结果</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="$sonarAddress">$sonarAddress</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">发布平台地址</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="http://adp.dm-ai.cn/#/deployment-management">http://adp.dm-ai.cn/#/deployment-management</a></td>
                        </tr>
                    </tbody>
                </table>

            <img src="data:image/png;base64,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" width="320" height="320">

            </body>
            </html>
            '''
        def textAndroid = '''
            <html>
            <head>
                <meta charset="utf-8">
                <!-- <meta name="viewport" content="width=device-width,initial-scale=1.0"> -->
                <!-- <title>y</title> -->
                <style type="text/css">
                    table.dataintable {
                        margin-top: 15px;
                        border-collapse: collapse;
                        border: 1px solid #aaa;
                        /* width: 100%; */
                        width: 1000px;
                    }
                    tr > td {
                        background: #f4f5f7;
                        height: 35px;
                        padding-left: 10px;
                        padding-right: 10px;
                        padding-top: 7px;
                        padding-bottom: 7px;
                        font-size: 16px;
                    }
                    tr > th {
                        height: 35px;
                        padding-left: 10px;
                        padding-right: 10px;
                        padding-top: 7px;
                        padding-bottom: 7px;
                        text-align: left;
                        font-size: 18px;
                    }
                </style>
            </head>
            <body>
                <h2 style="font-size: 22px; font-weight:bold;">构建结果如下：</h2>
                <table class="dataintable" border="1">
                    <tbody >
                        <tr style="background:#f7f5f4">
                            <th style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">名称</th>
                            <th style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">信息</th>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">构建项目</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">$appName</td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">Jenkins-构建地址(blue)</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a target="_blank" href="$jenkinsAddress/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline">Jenkins-blue-url</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">Jenkins-构建地址(old)</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a target="_blank" href="$jenkinsAddress/job/$jobName/job/$branchName">Jenkins-old-url</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">代码Git地址</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="$gitAddress">$gitAddress</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">代码Git分支</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">$branchName</td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">发布平台地址</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="http://adp.dm-ai.cn/#/deployment-management">adp-url</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">Android apk历史制品查看</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="http://192.168.69.32:8888/files/view/android_home/$appName">点我查看历时制品</a></td>
                        </tr>
                        <tr>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">Android apk当前构建制品下载</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="$apkViewUrl">点我直接下载</a><img src="data:image/png;base64, ${apkViewUrlQrcode}" width="200" height="200"></td>
                        </tr>
                    </tbody>
                </table>
            </body>
            </html>
            '''

        def bind = [
                'appName'        : this.conf.appName,
                'jenkinsAddress' : this.conf.jenkinsAddress,
                'jobName'        : this.conf.getAttr('jenkinsJobName'),
                'branchName'     : URLEncoder.encode(this.conf.getAttr('jenkinsBranchName'), 'UTF-8'),
                'buildNumber'    : this.conf.getAttr('buildNumber'),
                'buildResult'    : buildResult,
                'gitAddress'     : this.conf.getAttr('gitAddress'),
                'k8sWebAddress'  : this.conf.getK8sWebAddress(),
                'buildEnvInfo'   : this.buildEnvInfo().replaceAll('用户测试验证地址：', ''),
                'useSvcInfo'     : this.useSvcInfo(),
                'sonarAddress'   : 'http://sonar.ops.dm-ai.cn/dashboard?id=' + this.conf.appName,
                'adpUrlApp'      : this.adpUrlApp,
                'namespace'      : this.conf.getAttr('namespace'),
                'apkViewUrl'     : apkViewUrl,
                'apkViewUrlQrcode' :apkViewUrlQrcode
        ]
        if (conf.getAttr('codeLanguage') == 'android') {
            return Tools.simpleTemplate(textAndroid, bind)
        }else if (conf.getAttr('codeLanguage') != 'android') {
            return Tools.simpleTemplate(textComman, bind)
        }
    // return Tools.simpleTemplate(text, bind)
    }

    private String useSvcInfo() {
        if (!this.conf.getAttr('useService')) {
            return '此服务未使用service，无外部访问地址！'
        }
        return ''
    }

    private String buildEnvInfo() {
        // 兼容新版的域名地址 launcher-management-x2.deploy-env.dm-ai.cn
        if (this.conf.getAttr('domain') ) {
            if (this.conf.getAttr('https') || (this.conf.getAttr('https') == false && this.conf.getAttr('stageHttps') == true)) {
                return  '用户测试验证地址：' + 'https://' + this.conf.getAttr('domain')
            } else {
                return '用户测试验证地址：' + 'http://' + this.conf.getAttr('domain')
            }
        }

        if (this.conf.getAttr('useService') && this.conf.getAttr('svcType') == 'NodePort') {
            return '用户测试验证地址：' + this.conf.getAppUrl()
        }

        return ''
    }

}
