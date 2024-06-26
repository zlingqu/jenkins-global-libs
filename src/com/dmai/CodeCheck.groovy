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
            this.script.sh String.format("/sonar-scanner/bin/sonar-scanner -Dsonar.host.url=http://sonar.dm-ai.com -Dsonar.login=admin -Dsonar.password=33f5b945a8b5908793b6e -Dsonar.language=%s -Dsonar.projectName=%s -Dsonar.projectVersion=1.0 -Dsonar.sourceEncoding=UTF-8 -Dsonar.projectKey=%s -Dsonar.javascript.lcov.reportPaths=coverage/lcov.info -Dsonar.exclusions=test/**,node_modules/**,coverage/** -Dsonar.sources=./ -Dsonar.projectBaseDir=%s",
            this.getCodeLanguage(),
            this.conf.appName,
            this.conf.appName,
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
