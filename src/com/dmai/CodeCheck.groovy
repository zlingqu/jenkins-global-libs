package com.dmai

class CodeCheck {
    protected final def script
    private final Conf conf

    CodeCheck( script, Conf conf ){
        this.script = script
        this.conf = conf
    }

    // code check for sonar
    public void sonarCheck(){
        try {
//            this.script.sh  'sleep 1000'
            this.script.sh String.format("/sonar-scanner/bin/sonar-scanner -Dsonar.host.url=http://sonar.ops.dm-ai.cn -Dsonar.login=admin -Dsonar.password=33f5b945a8b5908793b6e -Dsonar.language=%s -Dsonar.projectName=%s -Dsonar.projectVersion=1.0 -Dsonar.sourceEncoding=UTF-8 -Dsonar.projectKey=%s -Dsonar.exclusions=**/node_modules/** -Dsonar.sources=./ -Dsonar.projectBaseDir=%s",
            this.getCodeLanguage(),
            this.conf.getAttr('namespace') + '-' + this.conf.appName,
            this.conf.getAttr('namespace') + '-' + this.conf.appName,
//            '/home/jenkins/workspace/' + this.conf.appName + '_' + this.conf.getAttr('branchName'))
            './')
        }
        catch (e) {
            this.script.sh "${e}"
        }
    }

    private String getCodeLanguage() {
        if (this.conf.getAttr('codeLanguage') == 'node') return 'js'
        return this.conf.getAttr('codeLanguage')
    }
}
