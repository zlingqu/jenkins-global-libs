package com.dmai

class DmaiEmail {

    protected final def script
    private Conf conf

    DmaiEmail(script, Conf conf) {
        this.script = script
        this.conf = conf
    }

    public sendEmail() {
        this.script.emailext (
                body: this.emailBody(conf, 'success'),
                subject: 'Jenkins build success info',
                to: conf.getAttr('emailAddress')
        )
    }

    private String emailBody(String buildResult) {
        return "测试！！！！！！"
    }
}
