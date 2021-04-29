package com.dmai

// 编译相关的逻辑
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
    
    public void compileOfNode() {

        this.script.sh String.format("mkdir -p /data/cache/%s/%s && "+
        "test -e package-lock.json && rm -f package-lock.json ;"+
        "mkdir -p node_modules &&" +
        "mount -t nfs 192.168.3.212:/devops/jenkins_artical/node_cache/%s/%s node_modules &&" +
        "npm config set registry https://npm.dm-ai.cn/repository/npm/ && npm install;", 
        this.conf.getAttr('namespace'),
        this.conf.getAttr('jobName'),
        this.conf.getAttr('namespace'),
        this.conf.getAttr('jobName'))
    }
    public void compileOfC() {
        this.script.sh "make"
    }
    public void compileOfJs() {

        this.script.sh String.format("mkdir -p /data/cache/%s/%s && "+
        "test -e package-lock.json && rm -f package-lock.json ;"+
        "mkdir -p node_modules &&" +
        "mount -t nfs 192.168.3.212:/devops/jenkins_artical/node_cache/%s/%s node_modules &&" +
        "export FRONTEND_ENV=%s; npm config set registry https://npm.dm-ai.cn/repository/npm/ && npm install && npm run build;",
        this.conf.getAttr('namespace'),
        this.conf.getAttr('jobName'),
        this.conf.getAttr('namespace'),
        this.conf.getAttr('jobName'),
        this.conf.getAttr('nodeEnv'))
    }
    public void compileOfUnity() {
        this.script.sh "test -e compile.sh && chmod +x ci/* && bash -x compile.sh && echo success || echo failure; test ! -e compile.sh && bash /opt/compile.sh && echo success || echo failure;"

    }

    public void compileOfAndroid() {
         this.script.sh "test -e /root/.gradle && rm -fr /root/.gradle; " +
                            "test -e /android_cache/.gradle-cache.tar && cp -rp /android_cache/.gradle-cache.tar /cache && rm -fr /cache/.gradle && tar xf /cache/.gradle-cache.tar -C /cache;" +
                            "test -e /android_cache/.gradle-root.tar && cp -rp /android_cache/.gradle-root.tar /root && tar xf /root/.gradle-root.tar -C /root;" +
                            "sh -x /opt/compile.sh ;" +
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
}
