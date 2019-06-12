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
            this.script.sh 'sleep 6000'
            this.script.sh "echo '${this.kubernetesDeployTemplate.getKubernetesDeployTemplate()}' > Deploy-k8s.yml"
        }

        this.script.sh 'kubectl apply -f Deploy-k8s.yml'
    }
}
