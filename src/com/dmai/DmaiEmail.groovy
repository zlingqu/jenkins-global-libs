package com.dmai

import com.tool.Tools
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

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
                    // body: this.emailBody(buildResultZh),
                    body: this.emailBody(buildResultZh),
                    subject: '应用名：' + this.conf.appName + ',构建 : ' + buildResultZh + '，分支：' + this.conf.getAttr('jenkinsBranchName') + '，部署环境：' + this.conf.getAttr('deployEnv'),
                    to: conf.getAttr('emailAddress') + ',quzhongling@dm-ai.cn,liaolonglong@dm-ai.cn'
            )
        }
        catch (e) {
            this.script.sh "${e}"
        }
    }

    private String emailBody(String buildResult) {
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
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">Git地址</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="$gitAddress">$gitAddress</a></td>
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
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;">Git地址</td>
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="$gitAddress">$gitAddress</a></td>
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
                            <td style="height: 35px;padding-left: 10px;padding-right: 10px;padding-top: 7px;padding-bottom: 7px;font-size: 18px;"><a href="http://192.168.69.32:8888/files/view/android_home/$appName/$deployEnv/$appName-build${buildNumber}-${gitCommit}.apk">点我直接下载</a></td>
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
                //                'buildTestInfo'  : this.buildTestInfo(),
                'useSvcInfo'     : this.useSvcInfo(),
                'sonarAddress'   : 'http://sonar.ops.dm-ai.cn/dashboard?id=' + this.conf.appName,
                'adpUrlApp'      : this.adpUrlApp,
                'namespace'      : this.conf.getAttr('namespace'),
                'deployEnv'      : this.conf.getAttr('deployEnv'),
                'buildNumber'    : this.conf.getAttr('buildNumber'),
                'gitCommit'      : this.conf.getAttr('gitVersion')
        ]
        if (conf.getAttr('codeLanguage') == 'android') {
            return Tools.simpleTemplate(textAndroid, bind)
        }else if (conf.getAttr('codeLanguage') != 'android'){
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
