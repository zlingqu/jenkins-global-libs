package com.dmai
import com.tool.Tools
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

class DmaiEmail {

    protected final def script
    private Conf conf
    private String adpUrl
    private String jenkinsUrl
    private String adpUrlApp

    DmaiEmail(script, Conf conf) {
        this.script = script
        this.conf = conf
        this.adpUrl = 'http://service-adp-deploy.dm-ai.cn/api/v1/deployments/change'
        this.jenkinsUrl = 'http://jenkins.ops.dm-ai.cn'
        this.adpUrlApp = 'http://app-deploy-platform.dm-ai.cn/#/project-management'
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

    public writeBuildResultToAdp(String buildResult) {

        if (buildResult == 'SUCCESS') {
            this.conf.setAttr('buildResult', 'success')
        }

        // 测试等待5秒后发送是否可以取到真实的值。
        TimeUnit.SECONDS.sleep(5)

        def jenkinsUrl = String.format('''%s/blue/organizations/jenkins/%s/detail/%s/%s/pipeline''', this.jenkinsUrl, this.conf.getAttr('jobName'), URLEncoder.encode(this.conf.getAttr('jenkinsBranchName'), "UTF-8"), this.conf.getAttr('buildNumber'))
        def status =  this.conf.getAttr('buildResult') == 'success' ? 'online' : 'failed'

        URL url = new URL(this.adpUrl)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()
        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type", "application/json")
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

    public sendEmail(String buildResult) {
//        this.conf.setAttr('buildResult', buildResult)

        //
        if (!this.conf.ifBuild()) {
            return
        }


//        this.writeBuildResultToAdp(buildResult)

        // 构建结果的中文提示：
        def buildResultZh = buildResult == 'success' ? '成功' : '失败: ' + conf.failMsg
        try {
            this.script.emailext(
                    body: this.emailBody(buildResultZh),
                    subject: "应用名：" + this.conf.appName+ ',构建 : ' + buildResultZh + "，分支：" + this.conf.getAttr('jenkinsBranchName') + "，部署环境：" + this.conf.getAttr('deployEnv'),
                    to: conf.getAttr('emailAddress') + ',zuosheng@dm-ai.cn'
            )
        }
        catch (e) {
            this.script.sh "${e}"
        }
    }

    private String emailBody(String buildResult) {
        def text = '''构建信息:
构建项目：$appName
$buildEnvInfo
其他服务调用当前服务地址：http://$appName
重要->服务之间的访问：服务之间访问请使用服务名,前端使用域名，非前端的测试开发域名用于日常开发调试，请coder注意。
Jenkins构建地址： $jenkinsAddress/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline
Git地址：$gitAddress
K8s管理页面地址：$k8sWebAddress
$useSvcInfo
sonar检查结果：$sonarAddress

特别说明：目前发布平台在建设中，master分支要上线到生产环境，请找运维人员(输密码)，手动上线，避免出现问题，master分支的自动上线已关闭, 自动上线默认失败！
发布平台地址：$adpUrlApp
'''
        def bind = [
                'appName'        : this.conf.appName,
                'jenkinsAddress' : this.conf.jenkinsAddress,
                'jobName'        : this.conf.getAttr('jenkinsJobName'),
                'branchName'     : URLEncoder.encode(this.conf.getAttr('jenkinsBranchName'), "UTF-8"),
                'buildNumber'    : this.conf.getAttr('buildNumber'),
                'buildResult'    : buildResult,
                'gitAddress'     : this.conf.getAttr('gitAddress'),
                'k8sWebAddress'  : this.conf.getK8sWebAddress(),
                'buildEnvInfo'   : this.buildEnvInfo(),
//                'buildTestInfo'  : this.buildTestInfo(),
                'useSvcInfo'     : this.useSvcInfo(),
                'sonarAddress'   : 'http://sonar.ops.dm-ai.cn/dashboard?id=' + this.conf.appName,
                'adpUrlApp'      : this.adpUrlApp
        ]
        return Tools.simpleTemplate(text, bind)
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
            if (this.conf.getAttr('https')) {
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
