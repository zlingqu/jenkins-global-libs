package com.dmai


// gpu如果模型和代码分别管理的，在制作镜像前，这里负责从某些地方获取模型文件

class ModelManage {

    protected final def script
    private final Conf conf

    ModelManage(script, Conf conf) {
        this.script = script
        this.conf = conf
    }
    public void modelGitManage() {
        try {
            this.script.sh 'source /etc/profile; git config --global http.sslVerify false ; git clone ' + this.conf.getAttr("modelGitRepository").replace("https://", 'https://dev-admin:46570e4250dc38fc48e3c61db@') + ' model'
            this.script.sh "pwd && ls -l && rm -fr model/.git"
        } catch (e) {
            this.script.sh "echo ${e}"
            this.conf.failMsg = '从gitlab下载模型文件失败！'
            throw e
        }
    }
    public void modelNfsManage() {
        try {
            this.script.sh String.format("mkdir -p %s; cp -rp /models/* %s",this.conf.getAttr('modelPath'),this.conf.getAttr('modelPath'))
        } catch (e) {
            this.script.sh "echo ${e}"
            this.conf.failMsg = '从gitlab下载模型文件失败！'
            throw e
        }
    }
    
}
