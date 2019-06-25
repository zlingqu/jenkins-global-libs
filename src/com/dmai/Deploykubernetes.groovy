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
        if (this.conf.getAttr('useConfigMap')) {
            this.createConfigMap()
            this.createConfigMapTest()
        }

        this.script.sh 'kubectl apply -f Deploy-k8s.yml'
    }

    private void createConfigMap() {

        try {
            this.script.sh String.format("kubectl apply -f deployment/%s/%s/%s/configmap.yml",
                    this.conf.getAttr('namespace'),
                    this.conf.getAttr(this.conf.getAttr('branchName')),
                    this.conf.appName
            )
        } catch (e) {
            this.script.sh "echo ${e}"
        }

        return
    }

    private void createConfigMapTest() {
        if (!this.conf.getAttr('test')) return

        try {
            this.script.sh String.format("kubectl apply -f deployment/%s/test/%s/configmap.yml",
                    this.conf.getAttr('namespace'),
                    this.conf.appName
            )
        } catch (e) {
            this.script.sh "echo ${e}"
        }

        return
    }
}
