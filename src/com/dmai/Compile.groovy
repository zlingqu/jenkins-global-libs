package com.dmai

class Compile {

    protected final def script
    private final Conf conf

    Compile(script, Conf conf) {
        this.script = script
        this.conf = conf
    }

    public void compileOfGolang() {
        this.script.sh "go env -w GOPRIVATE=gitlab.dm-ai.cn;go env -w GO111MODULE=on;export GOPROXY=https://mirrors.aliyun.com/goproxy/;make compile"
    }
    
    public void compileOfNodejs() {
        this.script.sh "test -e node_modules && rm -fr node_modules ; " +
                    "test -e /data/cache/node_modules/node_modules.tar && cp -rp /data/cache/node_modules/node_modules.tar ./ ; tar xf node_modules.tar && rm -fr node_modules.tar ; " +
                    "npm config set registry http://nexus.dm-ai.cn/repository/npm && npm install && tar cf node_modules.tar node_modules ; cp -rp node_modules.tar /data/cache/node_modules && rm -fr node_modules.tar"
    }
    public void compileOfC() {
        this.script.sh "make"
    }
    public void compileOfNodets() {
        this.script.sh "test -e node_modules && rm -fr node_modules ; " +
                            "test -e /data/cache/node_modules/node_modules.tar && cp -rp /data/cache/node_modules/node_modules.tar ./ ; tar xf node_modules.tar && rm -fr node_modules.tar ; " +
                            "npm config set registry http://nexus.dm-ai.cn/repository/npm && npm install && tsc && tar cf node_modules.tar node_modules ; cp -rp node_modules.tar /data/cache/node_modules && rm -fr node_modules.tar"
    }
    public void compileOfJs() {
        def tmpJsCompileString = 'npm config set registry https://npm.dm-ai.cn/repository/npm/ && npm install && npm run build || echo'
        if (this.conf.getAttr('ifCompileParam')) {
            tmpJsCompileString = this.conf.getAttr('compileParam')
        }
        this.script.sh String.format("test -e node_modules && rm -fr node_modules ; " +
                "test -e /data/cache/node_modules/node_modules.tar && cp -rp /data/cache/node_modules/node_modules.tar ./ ; tar xf node_modules.tar && rm -fr node_modules.tar ; " +
                "export FRONTEND_ENV=%s; %s && tar cf node_modules.tar node_modules;" +
                "cp -rp node_modules.tar /data/cache/node_modules && rm -fr node_modules.tar", this.conf.getAttr('nodeEnv'), tmpJsCompileString)
    }
    public void compileOfUnity() {
        this.script.sh "test -e compile.sh && chmod +x ci/* && bash -x compile.sh && echo success || echo failure; test ! -e compile.sh && bash /opt/compile.sh && echo success || echo failure;"

    }

    public void compileOfAndroid() {
         this.script.sh "test -e /root/.gradle && rm -fr /root/.gradle; " +
                            "test -e /android_cache/.gradle-cache.tar && cp -rp /android_cache/.gradle-cache.tar /cache && rm -fr /cache/.gradle && tar xf /cache/.gradle-cache.tar -C /cache;" +
                            "test -e /android_cache/.gradle-root.tar && cp -rp /android_cache/.gradle-root.tar /root && tar xf /root/.gradle-root.tar -C /root;" +
                            "sh -x /opt/compile.sh  " + "${this.conf.getAttr('compileParam')}; " +
                            "test -d /cache && cd /cache && tar cf .gradle-cache.tar .gradle && mv .gradle-cache.tar /android_cache;" +
                            "cd /root && tar cf .gradle-root.tar .gradle && mv .gradle-root.tar /android_cache"
    }
    public void compileOfJava() {
        if (this.conf.appName in ['work-attendance', 'ta-advanced-stats-server', 'ta-advanced-stats-server2', 'aita-advanced-stats-server']) {
            this.script.sh "mvn package -Dmaven.test.skip=true"
            
        } else {
        this.script.sh String.format('''mvn dm:package -Ddeploy.env=%s''', this.conf.getAttr('deployEnv'))
        }
    }



    // public void compile() {
    //     if (this.conf.getAttr('jobName') in ['ta-resource-service']) return
    //     if (this.conf.getAttr('compile')) {
    //         // wocao
    //         if (this.conf.appName in ['content-producer']) return

    //         if (this.conf.getAttr('customCompileCommand')) {
    //             if (this.conf.getAttr('codeLanguage') in ['js', 'nodes']) {
    //                 this.script.sh "test -e node_modules && rm -fr node_modules ; " +
    //                         "test -e /data/cache/node_modules/node_modules.tar && cp -rp /data/cache/node_modules/node_modules.tar ./ ; tar xf node_modules.tar && rm -fr node_modules.tar ; " + String.format("export FRONTEND_ENV=%s;", this.conf.getAttr('nodeEnv')) +
    //                         "${this.conf.getAttr('customCompileCommand')} && tar cf node_modules.tar node_modules ;" +
    //                         "cp -rp node_modules.tar /data/cache/node_modules && rm -fr node_modules.tar"
    //                 return
    //             }
    //             this.script.sh "${this.conf.getAttr('customCompileCommand')}"
    //             return
    //         }

    //         if (this.conf.appName == "work-attendance") {
    //             this.script.sh " package -Dmaven.test.skip=true; \
    //                                 test -e /root/.m2/target && rm -fr /root/.m2/target; \
    //                                 cp -rp target /root/.m2/; cd /root/.m2/target && mv work-attendance*.jar work-attendance.jar"
    //             return
    //         }

    //         switch (this.conf.getAttr('codeLanguage')) {
    //             case 'nodets':
    //                 this.script.sh "test -e node_modules && rm -fr node_modules ; " +
    //                         "test -e /data/cache/node_modules/node_modules.tar && cp -rp /data/cache/node_modules/node_modules.tar ./ ; tar xf node_modules.tar && rm -fr node_modules.tar ; " +
    //                         "npm config set registry http://nexus.dm-ai.cn/repository/npm && npm install && tsc && tar cf node_modules.tar node_modules ; cp -rp node_modules.tar /data/cache/node_modules && rm -fr node_modules.tar"
    //                 return
    //         }
    //     }
    // }
}
