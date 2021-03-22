package com.dmai

class Compile {

    protected final def script
    private final Conf conf

    Compile(script, Conf conf) {
        this.script = script
        this.conf = conf
    }

    public void modelGitManage() {
        try {
            withCredentials([usernamePassword(credentialsId: 'devops-use', passwordVariable: 'password', usernameVariable: 'username')]) {
                sh 'source /etc/profile; git config --global http.sslVerify false ; git clone ' + conf.getAttr("modelGitRepository").replace("https://", 'https://$username:$password@') + ' model'
            }
            sh 'pwd;ls -l;rm -fr model/.git'
        } catch (e) {
            sh "echo ${e}"
            conf.failMsg = '从gitlab下载模型文件失败！'
            throw e
        }
    }
    public void modelNfsManage() {
        try {
            sh "mkdir -p ${conf.getAttr('modelPath')}; cp -rp /models/* ${conf.getAttr('modelPath')}"
        } catch (e) {
            sh "echo ${e}"
            conf.failMsg = '从gitlab下载模型文件失败！'
            throw e
        }
    }
    
}
