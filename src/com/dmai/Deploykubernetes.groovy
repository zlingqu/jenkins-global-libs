package com.dmai

class Deploykubernetes {

    protected final def script
    private final Conf conf
    private KubernetesDeployTemplate kubernetesDeployTemplate

    Deploykubernetes( script, Conf conf ) {
        this.script = script
        this.conf = conf
        this.kubernetesDeployTemplate = new KubernetesDeployTemplate(this.conf)
    }

    public void deployKubernetes() {
        if (! conf.getAttr('customKubernetesDeployTemplate')) {
            try {

                this.script.sh "echo '${this.kubernetesDeployTemplate.getKubernetesDeployTemplate()}' > Deploy-k8s.yml"
            }
            catch (e) {
                this.script.sh "${e}"
            }
        }

        // 先创建configMap
//            this.createConfigMap()

        this.script.sh 'kubectl apply -f Deploy-k8s.yml'
    }

    private void createConfigMap() {
        if (! this.conf.getAttr('useConfigMap')) return
        switch (this.conf.appName) {
            case 'stat-service':
                this.script.sh String.format("kubectl delete configmap %s -n %s || echo 0", this.conf.appName, this.conf.getAttr('namespace'))
                this.script.sh "kubectl create configmap '${this.conf.appName}' --from-literal='${this.conf.getAttr('configMapName')}'='${this.conf.getAttr('configMapFile-dev')}' -n '${this.conf.getAttr('namespace')}'"
                return
        }

        // 如果使用了configmap，默认configmap的环境变量在代码目录下的env/dev，env/分支名下，master为管理员控制
        switch (this.conf.getAttr('branchName')) {
            case 'master':
                this.script.sh String.format("kubectl delete configmap %s -n %s || echo 0", this.conf.appName, this.conf.getAttr('namespace'))
                this.script.sh "kubectl create configmap '${this.conf.appName}' --from-literal='${this.conf.getAttr('configMapName')}'='${this.conf.getAttr('configMapFile')}' -n '${this.conf.getAttr('namespace')}'"

                return

            default:
                this.script.sh String.format("kubectl delete configmap %s -n %s || echo 0", this.conf.appName, this.conf.getAttr('namespace'))
                this.script.sh String.format("kubectl create configmap %s --from-file=%s=%s -n %s",
                this.conf.appName, this.conf.getAttr('configMapName'), this.conf.getAttr('configMapName') ,this.conf.getAttr('namespace'))
        }
    }
}
