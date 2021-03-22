package com.dmai

class ModelManage {

    protected final def script
    private final Conf conf

    ModelManage(script, Conf conf) {
        this.script = script
        this.conf = conf
    }

    public void modelGitManage() {
        try {
            withCredentials([usernamePassword(credentialsId: 'devops-use', passwordVariable: 'password', usernameVariable: 'username')]) {
                this.script.sh 'source /etc/profile; git config --global http.sslVerify false ; git clone ' + conf.getAttr("modelGitRepository").replace("https://", 'https://$username:$password@') + ' model'
            }
            this.script.sh 'pwd;ls -l;rm -fr model/.git'
        } catch (e) {
            this.script.sh "echo ${e}"
            conf.failMsg = '从gitlab下载模型文件失败！'
            throw e
        }
    }
    public void modelNfsManage() {
        try {
            this.script.sh "mkdir -p ${conf.getAttr('modelPath')}; cp -rp /models/* ${conf.getAttr('modelPath')}"
        } catch (e) {
            this.script.sh "echo ${e}"
            conf.failMsg = '从gitlab下载模型文件失败！'
            throw e
        }
    }
    
}
