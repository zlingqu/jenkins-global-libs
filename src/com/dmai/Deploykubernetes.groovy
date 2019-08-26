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

//        // 先创建configMap
//        if (this.conf.getAttr('useConfigMap')) {
//            this.createConfigMap()
//            this.createConfigMapTest()
//        }

        this.script.sh 'kubectl apply -f Deploy-k8s.yml; rm -fr Deploy-k8s.yml'
    }

    public void createConfigMap() {
        if (! this.conf.getAttr('useConfigMap')) return

        //新的使用.env的形式做兼容的方式。
//        if ( this.conf.getAttr('useEnvFile') ) {
//            try {
//                this.script.sh String.format("kubectl create cm %s --from-env-file deployment/%s/%s/%s/.env -n %s ",
//                this.conf.appName,
//                this.conf.getAttr('namespace'),
//                this.conf.getAttr(this.conf.getAttr('branchName')),
//                this.conf.appName)
//            } catch (e) {
//                this.script.sh "echo ${e}"
//            }
//            return
//        }

        // 老版的使用configmap的形式挂载文件
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

    public void createIngress() {
        if ( this.conf.getAttr('domain') == '' || ! this.conf.getAttr('domain') ) return

        try {
            this.script.sh String.format("kubectl apply -f deployment/%s/%s/%s/ingress.yml",
                    this.conf.getAttr('namespace'),
                    this.conf.getAttr(this.conf.getAttr('branchName')),
                    this.conf.appName
            )
        } catch (e) {
            this.script.sh "echo ${e}"
            this.script.sh "echo '${this.createIngressFile()}' > ingress.yml"
            this.script.sh "kubectl apply -f ingress.yml"
        }


        return
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

    private String createIngressFile() {
        return String.format('''
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: %s
  namespace: %s
  annotations:
    kubernetes.io/ingress.class: traefik
spec:
  rules:
  - host: %s%s
    http:
      paths:
      - backend:
          serviceName: %s
          servicePort: 80
''',
                this.conf.appName,
                this.conf.getAttr('namespace'),
                this.conf.getAttr('branchName') == 'master' ? '' : this.conf.getAttr('dev') + '.',
                this.conf.getAttr('domain'),
                this.conf.appName)
    }
}
