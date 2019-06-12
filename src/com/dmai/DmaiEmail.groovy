package com.dmai

class DmaiEmail {

    protected final def script
    private Conf conf

    DmaiEmail(script, Conf conf) {
        this.script = script
        this.conf = conf
    }

    public sendEmail() {
        try {
            this.script.emailext(
                    body: this.emailBody('success'),
                    subject: 'Jenkins build success info',
                    to: conf.getAttr('emailAddress')
            )
        }
        catch (e) {
            this.script.sh "${e}"
        }
    }

    private String emailBody(String buildResult) {
        return "测试！！！！！！${buildResult}"
    }
}
