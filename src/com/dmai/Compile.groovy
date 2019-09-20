package com.dmai

class Compile {

    protected final def script
    private final Conf conf

    Compile( script, Conf conf ){
        this.script = script
        this.conf = conf
    }

    public void compile(){
        if (this.conf.getAttr('compile')) {
            if (this.conf.getAttr('customCompileCommand')) {
                this.script.sh "${this.conf.getAttr('customCompileCommand')}"
                return
            }

            if (this.conf.appName == "work-attendance") {
                this.script.sh "mvn package -Dmaven.test.skip=true; \
                                    test -e /root/.m2/target && rm -fr /root/.m2/target; \
                                    cp -rp target /root/.m2/; cd /root/.m2/target && mv work-attendance*.jar work-attendance.jar"
                return
            }

            switch (this.conf.getAttr('codeLanguage')) {
                case 'node':
                    this.script.sh "test -e node_modules && rm -fr node_modules ; " +
                            "test -e /data/cache/node_modules/node_modules.tar && cp -rp /data/cache/node_modules/node_modules.tar ./ ; tar xf node_modules.tar && rm -fr node_modules.tar ; " +
                            "npm config set registry http://192.168.3.13:8081/repository/npm && npm install && tar cf node_modules.tar node_modules ; cp -rp node_modules.tar /data/cache/node_modules && rm -fr node_modules.tar"
                    return
                case 'js':
                    this.script.sh String.format("export FRONTEND_ENV=%s;npm config set registry http://192.168.3.13:8081/repository/npm/ && yarn install && yarn run build", this.conf.getAttr('nodeEnv'))
                    return
                case 'c++':
                    this.script.sh "make"
                    return
                case 'java':
                    this.script.sh "mvn package -Dmaven.test.skip=true"
            }
        }
    }
}
