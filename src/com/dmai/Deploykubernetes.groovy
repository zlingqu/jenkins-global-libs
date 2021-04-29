package com.dmai

class Deploykubernetes {

    protected final def script
    private final Conf conf

    Deploykubernetes(script, Conf conf) {
        this.script = script
        this.conf = conf
    }

    public void deployKubernetes() {

        if (this.conf.getAttr('customKubernetesDeployTemplate')) {
            if (this.conf.getAttr('autoDeployContent')) {
                this.script.sh "echo '${this.conf.getAttr('autoDeployContent')}' > Deploy-k8s.yml"
            } else {
                // 如果 Deploy-k8s.yml 还不存在，说明，当前代码目录下没有文件，用deplayment下面的文件

                def deployK8eUrl = String.format('https://gitlab.dm-ai.cn/devops/adp/deployment/raw/' + '/%s/%s/%s/Deploy-k8s.yml?private_token=zXswJbwzgd3Smarcd4pD',
                        this.conf.getAttr('namespace'),
                        this.conf.getAttr('deployEnv'),
                        this.conf.appName)
                def deploymentDeployK8sFile = 'Deploy-k8s.yml'
                try {
                    this.script.sh "wget -o ${deploymentDeployK8sFile} ${deployK8eUrl}"
                } catch (e) {
                    this.script.sh "${e}"
                }
                this.script.sh "test -e  Deploy-k8s.yml && sed -i s#JENKINS_DEPLOY_IMAGE_ADDRESS#${this.conf.getAttr('buildImageAddress')}#g Deploy-k8s.yml"
                this.script.sh 'cat Deploy-k8s.yml'
            }
        }

        this.script.sh String.format("mkdir -p ~/.kube && wget http://adp-api.dm-ai.cn/api/v1/get-k8s-key-file?env='%s' -O ~/.kube/config", this.conf.getAttr('deployEnv'))
        this.script.sh 'kubectl apply -f Deploy-k8s.yml; rm -fr Deploy-k8s.yml'
    }

    public String kubectlDeployment(String format) {
        return "kubectl apply -f https://gitlab.dm-ai.cn/devops/adp/deployment/raw/" + format + '?private_token=zXswJbwzgd3Smarcd4pD'
    }

    public void deleteOldIngress() {
        this.script.sh String.format('kubectl delete ing %s -n %s || echo 0', this.conf.getAttr('jobName'), this.conf.getAttr('namespace'))
    }

}
