package com.dmai

import com.tool.Tools
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

// import static java.nio.charset.StandardCharsets.UTF_8

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
                    to: this.conf.getAttr('emailAddress')
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
        def buildResultZh = buildResult == 'success' ? '成功' : '失败: ' + this.conf.failMsg
        try {
            this.script.emailext(
                    body: this.emailBody(buildResultZh),
                    subject: this.conf.appName + ',构建 : ' + buildResultZh + '，部署环境：' + this.conf.getAttr('deployEnv'),
                    to: this.conf.getAttr('emailAddress') + ',quzhongling@dm-ai.cn,taochao@dm-ai.cn'
            )
        }
        catch (e) {
            this.script.sh "${e}"
        }
    }

    private String emailBody(String buildResult) {
        def String apkViewUrl = ''
        def String apkViewUrlQrcode = ''
        if (this.conf.getAttr('codeLanguage') == 'android') {
            apkViewUrl = String.format('''http://models.jenkins.dm-ai.cn:8888/files/view/android_home/%s/%s/%s/%s-build%s-%s.apk''',
                    this.conf.appName,
                    this.conf.getAttr('deployEnv'),
                    new Date().format('yyyyMMdd'),
                    this.conf.appName,
                    this.conf.getAttr('buildNumber'),
                    this.conf.getAttr('gitVersion')
                )

            def url = 'curl -s adp-api.dm-ai.cn/api/v1/tools/qrcode?url=' + apkViewUrl + '|base64'
            apkViewUrlQrcode = this.script.sh(returnStdout: true, script: url).trim()
        }
        def textComman = '''
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <base target="_blank">
                <!-- base 标签用来覆盖<a>、<img>、<link>、<form>等标签中的某些属性  -->
                <style type="text/css">
                    table.t1 {
                        border-collapse: collapse;
                        border: 1px solid rgb(39, 39, 41);
                        width: 50%;
                    }
                    td,th {
                        background: #f4f5f7;
                        height: 35px;
                        padding: 7px 10px;
                        font-size: 18px;
                    }

                    th {
                        background-color: rgb(162, 161, 224);
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <h2 style="font-size: 22px; font-weight:bold;">构建结果如下：</h2>
                <table class="t1" border="1">
                    <tbody style="">
                        <tr style="background-color: rgb(162, 161, 224);">
                            <th style="text-align: center">序号</th>
                            <th>类别</th>
                            <th>内容</th>
                        </tr>
                        <tr>
                            <td style="text-align: center">1</td>
                            <td>构建项目</td>
                            <td>$appName</td>
                        </tr>
                        <tr>
                            <td style="text-align: center">2</td>
                            <td>k8s外部访问地址</td>
                            <td><a href="$buildEnvInfo">$buildEnvInfo</a></td>
                        </tr>
                        <tr>
                            <td style="text-align: center">3</td>
                            <td>k8s内部访问地址</td>
                            <td>http://$appName.$namespace</td>
                        </tr>
                        <tr>
                            <td style="text-align: center">4</td>
                            <td>K8s Dashboard查看</td>
                            <td> <a href="$k8sWebAddress">$k8sWebAddress</a></td>
                        </tr>
                        <tr>
                            <td style="text-align: center">5</td>
                            <td>sonar检查结果</td>
                            <td> <a href="$sonarAddress">$sonarAddress</a> </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">6</td>
                            <td>Jenkins-构建地址(blue)</td>
                            <td> <a href="$jenkinsAddress/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline">Jenkins-blue-url</a> </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">7</td>
                            <td>Jenkins-构建地址(old)</td>
                            <td> <a href="$jenkinsAddress/job/$jobName/job/$branchName">Jenkins-old-url</a></td>
                        </tr>
                        <tr>
                            <td style="text-align: center">8</td>
                            <td>代码Git地址</td>
                            <td><a href="$gitAddress">$gitAddress</a></td>
                        </tr>
                        <tr>
                            <td style="text-align: center">9</td>
                            <td>代码Git分支</td>
                            <td>$branchName</td>
                        </tr>
                        <tr>
                            <td style="text-align: center">10</td>
                            <td>发布平台地址</td>
                            <td> <a href="http://adp.dm-ai.cn/#/deployment-management">http://adp.dm-ai.cn/#/deployment-management</a></td>
                        </tr>
                    </tbody>
                </table>
            </body>

            </html>
            '''
        def textAndroid = '''
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
                <base target="_blank">
                <!-- base 标签用来覆盖<a>、<img>、<link>、<form>等标签中的某些属性  -->
                <style type="text/css">
                    table.t1 {
                        border-collapse: collapse;
                        border: 1px solid rgb(39, 39, 41);
                        width: 50%;
                    }
                    td,th {
                        background: #f4f5f7;
                        height: 35px;
                        padding: 7px 10px;
                        font-size: 18px;
                    }

                    th {
                        background-color: rgb(162, 161, 224);
                        text-align: center;
                    }
                </style>
            </head>

            <body>
                <h2 style="font-size: 22px; font-weight:bold;">构建结果如下：</h2>
                <table class="t1" border="1">
                    <tbody>
                        <tr>
                            <th style="text-align: center">序号</th>
                            <th>类别</th>
                            <th>内容</th>
                        </tr>
                        <tr>
                            <td style="text-align: center">1</td>
                            <td>构建项目</td>
                            <td>$appName</td>
                        </tr>
                        <tr>
                            <td style="text-align: center">2</td>
                            <td>Jenkins-构建地址(blue)</td>
                            <td> <a href="$jenkinsAddress/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline">Jenkins-blue-url</a> </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">3</td>
                            <td>Jenkins-构建地址(old)</td>
                            <td> <a href="$jenkinsAddress/job/$jobName/job/$branchName">Jenkins-old-url</a></td>
                        </tr>
                        <tr>
                            <td style="text-align: center">4</td>
                            <td>代码Git地址</td>
                            <td><a href="$gitAddress">$gitAddress</a></td>
                        </tr>
                        <tr>
                            <td style="text-align: center">5</td>
                            <td>代码Git分支</td>
                            <td>$branchName</td>
                        </tr>
                        <tr>
                            <td style="text-align: center">6</td>
                            <td>发布平台地址</td>
                            <td> <a href="http://adp.dm-ai.cn/#/deployment-management">http://adp.dm-ai.cn/#/deployment-management</a></td>
                        </tr>
                        <tr>
                            <td style="text-align: center">7</td>
                            <td> Android apk历史制品查看</td>
                            <td> <a href="http://models.jenkins.dm-ai.cn:8888/files/view/android_home/$appName">点我查看历时制品</a></td>
                        </tr>
                        <tr>
                            <td style="text-align: center">8</td>
                            <td> Android apk当前构建制品下载</td>
                            <td> <a href="$apkViewUrl">1、点我直接下载</a> <br> 2、扫描二维码下载<img style="vertical-align:middle" src="data:image/png;base64, $apkViewUrlQrcode" width="200" height="200" alt="无法加载二维码"> </td>
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
        if (this.conf.getAttr('codeLanguage') == 'android') {
            return Tools.simpleTemplate(textAndroid, bind)
        }else if (this.conf.getAttr('codeLanguage') != 'android') {
            return Tools.simpleTemplate(textComman, bind)
        }
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
