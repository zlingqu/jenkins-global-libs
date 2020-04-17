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
        if (! this.conf.getAttr('customKubernetesDeployTemplate')) {
            try {

                this.script.sh "echo '${this.kubernetesDeployTemplate.getKubernetesDeployTemplate()}' > Deploy-k8s.yml"
            }
            catch (e) {
                this.script.sh "${e}"
            }
        }

        //
        if (this.conf.getAttr('customKubernetesDeployTemplate') && this.conf.getAttr('autoDeployContent')) {
            this.script.sh "echo '${this.conf.getAttr('autoDeployContent')}' > Deploy-k8s.yml"
        }

        // 如果 Deploy-k8s.yml 还不存在，说明，当前代码目录下没有文件，用deplayment下面的文件

        def deployFileTemplate = String.format('''deployment/%s/%s/%s/Deploy-k8s.yml''',  this.conf.getAttr('namespace'), this.conf.getAttr('deployEnv'), this.conf.appName)
        this.script.sh "test -e  Deploy-k8s.yml || cat ${deployFileTemplate}  | sed s#JENKINS_DEPLOY_IMAGE_ADDRESS#${this.conf.getAttr('buildImageAddress')}#g > Deploy-k8s.yml"
        this.script.sh "test -e  Deploy-k8s.yml && sed -i s#JENKINS_DEPLOY_IMAGE_ADDRESS#${this.conf.getAttr('buildImageAddress')}#g Deploy-k8s.yml"
        this.script.sh "cat Deploy-k8s.yml"

        this.script.sh 'kubectl apply -f Deploy-k8s.yml; rm -fr Deploy-k8s.yml'
    }

    public void createConfigMap() {
        if (! this.conf.getAttr('useConfigMap')) return

        // 老版的使用configmap的形式挂载文件
        try {
            this.script.sh String.format("kubectl apply -f deployment/%s/%s/%s/configmap.yml",
                    this.conf.getAttr('namespace'),
//                    this.conf.getAttr('branchName') in ['master', 'dev'] ? this.conf.getAttr(this.conf.getAttr('branchName')) : this.conf.getAttr('branchName'),
                    this.conf.getAttr('deployEnv'),
                    this.conf.appName
            )
        } catch (e) {
            this.script.sh "echo ${e}"
        }

        return
    }

    public void createIngress() {
        if ( this.conf.getAttr('domain') == '' || ! this.conf.getAttr('domain') ) return

        try {
            this.script.sh String.format("kubectl apply -f deployment/%s/%s/%s/ingress.yml",
                    this.conf.getAttr('namespace'),
//                    this.conf.getAttr('branchName') in ['master', 'dev'] ? this.conf.getAttr(this.conf.getAttr('branchName')) : this.conf.getAttr('branchName'),
                    this.conf.getAttr('deployEnv'),
                    this.conf.appName
            )
        } catch (e) {
            this.script.sh "echo ${e}"
            this.script.sh "echo '${this.createIngressFile()}' > ingress.yml"
            this.script.sh "kubectl apply -f ingress.yml"
        }


        return
    }

    public void deleteOldIngress() {
        this.script.sh String.format("kubectl delete ing %s -n %s || echo 0", this.conf.getAttr('jobName'), this.conf.getAttr('namespace'))
        this.script.sh String.format("kubectl delete IngressRoute %s -n %s || echo 0", this.conf.getAttr('jobName'), this.conf.getAttr('namespace'))
        this.script.sh String.format("kubectl delete IngressRoute %s -n %s || echo 0", this.conf.getAttr('jobName') + '-https', this.conf.getAttr('namespace'))
    }

    public void createConfigMapTest() {
        if (! this.conf.getAttr('useConfigMap')) return
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

    private String  createIngressFile() {
        return String.format('''
apiVersion: networking.k8s.io/v1beta1
kind: Ingress
metadata:
  name: %s
  namespace: %s
  annotations:
    kubernetes.io/ingress.class: traefik
spec:
  rules:
  - host: %s
    http:
      paths:
      - backend:
          serviceName: %s
          servicePort: 80
''',
                this.conf.appName,
                this.conf.getAttr('namespace'),
                this.conf.getAttr('domain'),
                this.conf.appName)
    }
}
