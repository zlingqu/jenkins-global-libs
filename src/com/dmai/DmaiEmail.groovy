package com.dmai
import com.tool.Tools

class DmaiEmail {

    protected final def script
    private Conf conf
    private String adpUrl
    private String jenkinsUrl

    DmaiEmail(script, Conf conf) {
        this.script = script
        this.conf = conf
        this.adpUrl = 'http://192.168.3.21:30102/api/v1/deployments/change'
        this.jenkinsUrl = 'http://jenkins.ops.dm-ai.cn'
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
        def jenkinsUrl = String.format('''%s/blue/organizations/jenkins/%s/detail/%s/%s/pipeline''', this.jenkinsUrl, this.conf.getAttr('jobName'), this.conf.getAttr('branchName'), this.conf.getAttr('buildNumber'))
        def status =  buildResult == 'success' ? 'online' : 'failed'

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
        //
        this.writeBuildResultToAdp(buildResult)

        // 构建结果的中文提示：
        def buildResultZh = buildResult == 'success' ? '成功' : '失败'
        try {
            this.script.emailext(
                    body: this.emailBody(buildResultZh),
                    subject: '构建 : ' + buildResultZh + "，应用名：" + this.conf.appName+ "，分支：" + this.conf.getAttr('branchName'),
                    to: conf.getAttr('emailAddress')
            )
        }
        catch (e) {
            this.script.sh "${e}"
        }
    }

    private String emailBody(String buildResult) {
        def text = '''Jenkins构建信息
构建项目：$jobName
特别说明：目前发布平台在建设中，master分支要上线到生产环境，请找运维人员(输密码)，手动上线，避免出现问题，master分支的自动上线已关闭, 自动上线默认失败！
构建结果：$buildResult
Jenkins构建地址： $jenkinsAddress/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline
构建分支：$branchName
Git地址：$gitAddress
$useSvcInfo
$buildEnvInfo
$buildTestInfo
sonar检查结果：$sonarAddress
'''
        def bind = [
                'jenkinsAddress' : this.conf.jenkinsAddress,
                'jobName'        : this.conf.getAttr('jobName'),
                'branchName'     : this.conf.getAttr('branchName'),
                'buildNumber'    : this.conf.getAttr('buildNumber'),
                'buildResult'    : buildResult,
                'gitAddress'     : this.conf.getAttr('gitAddress'),
                'buildEnvInfo'   : this.buildEnvInfo(),
                'buildTestInfo'  : this.buildTestInfo(),
                'useSvcInfo'     : this.useSvcInfo(),
                'sonarAddress'   : 'http://sonar.ops.dm-ai.cn/dashboard?id=' + this.conf.appName
        ]
        return Tools.simpleTemplate(text, bind)
    }

//    private String getDomainInfo() {
//        if (!(this.conf.getAttr('domain') in [null, ''])) {
//            return '应用域名地址：' + this.conf.getAttr('domain')
//        }
//        return ''
//    }

    private String useSvcInfo() {
        if (!this.conf.getAttr('useService')) {
            return '此服务未使用service，无外部访问地址！'
        }
        return ''
    }

    private String buildEnvInfo() {
        // 如果用户设置了域名地址
        if (!(this.conf.getAttr('domain') in [null, ''])) {
            return '构建完成, 用户访问地址：'  + this.getDomainTopString() + this.conf.getAttr('domain')
        }

        if (this.conf.getAttr('useService') && this.conf.getAttr('svcType') == 'NodePort') {
            return '构建完成, 用户访问地址：' + this.getAppUrl()
        }

        return ''
    }

    private String getDomainTopString() {
        if (this.conf.getAttr('branchName') == 'master') {
            return 'http://'
        }
        return 'http://' + this.conf.getAttr('branchName') + '.'
//        return 'http://' + this.conf.getAttr('branchName') == 'master' ? '' : this.conf.getAttr('branchName') + '.'
    }

    private String buildTestInfo() {
        if (!(this.conf.getAttr('domain') in [null, ''])) {
            return '测试环境, 用户访问地址：'  + 'http://test.' + this.conf.getAttr('domain')
        }

        if (this.conf.getAttr('test')) {
            return '测试环境, 用户访问地址：' + this.getTestAddress()
        }
        return ''
    }

    private String getTestAddress() {
        if (this.conf.getAttr('test')) {
            return 'http://192.168.3.140:' + this.conf.getAttr('nodePort')
        }

        return '用户未部署测试分支'
    }

    private String getAppUrl() {
        switch (this.conf.getAttr('svcType')) {
            case 'ClusterIP':
                return '用户使用的svc模式为ClusterIP,外部无法直接访问。'
            case 'NodePort':
                return this.nodePortAddress() + ':' + this.conf.getAttr('nodePort')
        }
    }

    private String nodePortAddress() {
        switch (this.conf.getAttr('branchName')) {
            case 'master':
                return 'http://192.168.11.20'
            case 'dev':
                return this.getDevUrl()
        }
    }

//    根据dev标签来判断用户的dev分支部署在那个环境
    private String getDevUrl() {
        switch (this.conf.getAttr('deployEnv')) {
            case 'test':
                return 'http://192.168.3.140'
            case 'dev':
                return 'http://192.168.3.21'
            case 'master':
                return 'http://192.168.11.20'
        }
    }
}
