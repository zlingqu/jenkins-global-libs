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
            switch (this.conf.getAttr('codeLanguage')) {
                case 'node':
                    this.script.sh "test -e node_modules && rm -fr node_modules; " +
                            "cp -rp /data/cache/node_modules ./; " +
                            "npm config set registry http://192.168.3.13:8081/repository/npm && npm install;" +
                            "rm -fr  /data/cache/node_modules/*; " +
                            "cp -rp node_modules/* /data/cache/node_modules/"
                    return
                case 'js':
                    this.script.sh "yarn config set registry=http://192.168.3.13:8081/repository/npm/ && yarn install && yarn run build"
                    return
                case 'c++':
                    this.script.sh "make"
                    return
                case 'java':
                    this.script.sh "mvn package -Dmaven.test.skip=true; \
                                    test -e /root/.m2/target && rm -fr /root/.m2/target; \
                                    cp -rp target /root/.m2/; cd /root/.m2/target && mv work-attendance*.jar work-attendance.jar"
            }
        }
    }
}
