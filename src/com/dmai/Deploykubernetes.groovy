package com.dmai

class Deploykubernetes {

    protected final def script
    private final Conf conf
    private KubernetesDeployTemplate kubernetesDeployTemplate

    Deploykubernetes(script, Conf conf) {
        this.script = script
        this.conf = conf
        this.kubernetesDeployTemplate = new KubernetesDeployTemplate(this.conf)
    }

    public void deployKubernetes() {
        if (!this.conf.getAttr('customKubernetesDeployTemplate')) {
            try {

                this.script.sh "echo '${this.kubernetesDeployTemplate.getKubernetesDeployTemplate()}' > Deploy-k8s.yml"
            }
            catch (e) {
                this.script.sh "${e}"
            }
        }

        //
        if (this.conf.getAttr('customKubernetesDeployTemplate')) {
            if(this.conf.getAttr('autoDeployContent')){
                this.script.sh "echo '${this.conf.getAttr('autoDeployContent')}' > Deploy-k8s.yml"
            } else {
                // 如果 Deploy-k8s.yml 还不存在，说明，当前代码目录下没有文件，用deplayment下面的文件

                def deployK8eUrl = String.format("https://gitlab.dm-ai.cn/application-engineering/devops/deployment/raw/" + this.conf.getAttr("branchName") + "/%s/%s/%s/Deploy-k8s.yml?private_token=zXswJbwzgd3Smarcd4pD",
                        this.conf.getAttr('namespace'),
                        this.conf.getAttr('deployEnv'),
                        this.conf.appName)
                def deploymentDeployK8sFile = "deployment-Deploy-k8s.yml"
                try {
                    this.script.sh "wget -o ${deploymentDeployK8sFile} ${deployK8eUrl}"
                } catch (e) {
                    this.script.sh "${e}"
                }

            }
            
        }

        
        this.script.sh "test -e  Deploy-k8s.yml || cat ${deploymentDeployK8sFile}  | sed s#JENKINS_DEPLOY_IMAGE_ADDRESS#${this.conf.getAttr('buildImageAddress')}#g > Deploy-k8s.yml"
        this.script.sh "test -e  Deploy-k8s.yml && sed -i s#JENKINS_DEPLOY_IMAGE_ADDRESS#${this.conf.getAttr('buildImageAddress')}#g Deploy-k8s.yml"
        this.script.sh "cat Deploy-k8s.yml"
        this.script.sh String.format("mkdir -p ~/.kube && wget http://adp-api.dm-ai.cn/api/v1/get-k8s-key-file?env='%s' -O ~/.kube/config", this.conf.getAttr("deployEnv"))
        this.script.sh 'kubectl apply -f Deploy-k8s.yml; rm -fr Deploy-k8s.yml'
    }

    public String kubectlDeployment(String format) {
        return "kubectl apply -f https://gitlab.dm-ai.cn/application-engineering/devops/deployment/raw/${this.conf.getAttr("branchName")}/" + format + "?private_token=zXswJbwzgd3Smarcd4pD"
    }

    public void createConfigMap(isTest) {
        if (!this.conf.getAttr('useConfigMap')) return

        // 老版的使用configmap的形式挂载文件
        try {
            this.script.sh String.format(kubectlDeployment("%s/%s/%s/configmap.yml"),
                    this.conf.getAttr('namespace'),
//                    this.conf.getAttr('branchName') in ['master', 'dev'] ? this.conf.getAttr(this.conf.getAttr('branchName')) : this.conf.getAttr('branchName'),
                    isTest ? 'test' : this.conf.getAttr('deployEnv'),
                    this.conf.appName
            )
        } catch (e) {
            this.script.sh "echo ${e}"
        }
    }

    public void createIngress() {
        if (this.conf.getAttr('domain') == '' || !this.conf.getAttr('domain')) return

        try {
            this.script.sh String.format(kubectlDeployment("%s/%s/%s/ingress.yml"),
                    this.conf.getAttr('namespace'),
//                    this.conf.getAttr('branchName') in ['master', 'dev'] ? this.conf.getAttr(this.conf.getAttr('branchName')) : this.conf.getAttr('branchName'),
                    this.conf.getAttr('deployEnv'),
                    this.conf.appName
            )
        } catch (e) {
            this.script.sh "echo ${e}"
            this.script.sh "echo '${this.createIngressFile()}' > ingress.yml"
            this.script.sh "cat ingress.yml"
            this.script.sh "kubectl apply -f ingress.yml"
        }


        return
    }

    public void deleteOldIngress() {
        this.script.sh String.format("kubectl delete ing %s -n %s || echo 0", this.conf.getAttr('jobName'), this.conf.getAttr('namespace'))
//        trafic 2.0后不需要删除 IngressRoute
//        this.script.sh String.format("kubectl delete IngressRoute %s -n %s || echo 0", this.conf.getAttr('jobName'), this.conf.getAttr('namespace'))
//        this.script.sh String.format("kubectl delete IngressRoute %s -n %s || echo 0", this.conf.getAttr('jobName') + '-https', this.conf.getAttr('namespace'))
    }


    private String createIngressFile() {
        return String.format('''
apiVersion: traefik.containo.us/v1alpha1
kind: IngressRoute
metadata:
  name: %s
  namespace: %s
spec:
  entryPoints:
  - web
  routes:
  - match: Host(`%s`)
    kind: Rule
    services:
    - name: %s
      port: 80
''',
                this.conf.appName,
                this.conf.getAttr('namespace'),
                this.conf.getAttr('domain'),
                this.conf.appName)
    }
}
