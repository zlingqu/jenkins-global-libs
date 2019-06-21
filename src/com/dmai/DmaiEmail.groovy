package com.dmai
import com.tool.Tools

class DmaiEmail {

    protected final def script
    private Conf conf

    DmaiEmail(script, Conf conf) {
        this.script = script
        this.conf = conf
    }

    public sendEmail(String buildResult) {
        try {
            this.script.emailext(
                    body: this.emailBody(buildResult),
                    subject: 'Jenkins build success info',
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
构建结果：$buildResult
Jenkins构建地址： $jenkinsAddress/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline
构建分支：$branchName
构建完成，用户访问地址：$appurl
'''
        def bind = [
                'jenkinsAddress' : this.conf.jenkinsAddress,
                'jobName'        : this.conf.getAttr('jobName'),
                'branchName'     : this.conf.getAttr('branchName'),
                'buildNumber'    : this.conf.getAttr('buildNumber'),
                'appurl'         : this.getAppUrl(),
                'buildResult'    : buildResult
        ]
        return Tools.simpleTemplate(text, bind)
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
        switch (this.conf.getAttr('dev')) {
            case 'test':
                return 'http://192.168.3.140'
            case 'dev':
                return 'http://192.168.3.18'
            case 'master':
                return 'http://192.168.11.20'
        }
    }
}
