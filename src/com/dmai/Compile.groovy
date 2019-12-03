package com.dmai

class Compile {

    protected final def script
    private final Conf conf

    Compile( script, Conf conf ){
        this.script = script
        this.conf = conf
    }

    public void compile(){
        if (this.conf.getAttr('compile') || this.conf.getAttr('codeLanguage') == 'node') {
            // wocao
            if (this.conf.appName in ['content-producer']) return

            if (this.conf.getAttr('customCompileCommand')) {
                if (this.conf.getAttr('codeLanguage') in ['js', 'nodes']) {
                    this.script.sh "test -e node_modules && rm -fr node_modules ; " +
                            "test -e /data/cache/node_modules/node_modules.tar && cp -rp /data/cache/node_modules/node_modules.tar ./ ; tar xf node_modules.tar && rm -fr node_modules.tar ; " +
                            "${this.conf.getAttr('customCompileCommand')} && tar cf node_modules.tar node_modules ;" +
                            "cp -rp node_modules.tar /data/cache/node_modules && rm -fr node_modules.tar"
                    return
                }
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
                    this.script.sh String.format("test -e node_modules && rm -fr node_modules ; " +
                            "test -e /data/cache/node_modules/node_modules.tar && cp -rp /data/cache/node_modules/node_modules.tar ./ ; tar xf node_modules.tar && rm -fr node_modules.tar ; " +
                            "export FRONTEND_ENV=%s;npm config set registry http://192.168.3.13:8081/repository/npm/ && yarn config set registry http://192.168.3.13:8081/repository/npm/ && yarn install && yarn run build && tar cf node_modules.tar node_modules;" +
                            "cp -rp node_modules.tar /data/cache/node_modules && rm -fr node_modules.tar", this.conf.getAttr('nodeEnv'))
                    return
                case 'c++':
                    this.script.sh "make"
                    return
                case 'java':
//                    this.script.sh "mvn package -Dmaven.test.skip=true"
                    if (this.conf.appName in ['work-attendance', 'ta-advanced-stats-server']) {
                        this.script.sh "mvn package -Dmaven.test.skip=true"
                        return
                    }
                    this.script.sh String.format('''mvn dm:package -Ddeploy.env=%s''', this.conf.getAttr('deployEnv'))
                    return
                case 'android':
                    this.script.sh "test -e /root/.gradle && rm -fr /root/.gradle; " +
                            "test -e /android_cache/.gradle-cache.tar && cp -rp /android_cache/.gradle-cache.tar /cache && rm -fr /cache/.gradle && tar xf /cache/.gradle-cache.tar -C /cache;" +
                            "test -e /android_cache/.gradle-root.tar && cp -rp /android_cache/.gradle-root.tar /root && tar xf /root/.gradle-root.tar -C /root;" +
                            "bash -x compile.sh  " + "${this.conf.getAttr('compileParam')} && " +
                            "cd /cache && tar cf .gradle-cache.tar .gradle && mv .gradle-cache.tar /android_cache && " +
                            "cd /root && tar cf .gradle-root.tar .gradle && mv .gradle-root.tar /android_cache"
                    return
                case 'golang':
                    this.script.sh "make compile"
                    return
            }
        }
    }
}
