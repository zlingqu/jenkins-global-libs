package com.dmai
import com.tool.Tools

class KubernetesDeployTemplate {

    private Conf conf

    KubernetesDeployTemplate(Conf conf) {
        this.conf = conf
    }

    public String getKubernetesDeployTemplate() {
        return this.getSvcTemplate() + this.getDeploymentTemplate()
    }

    private String getSvcTemplate() {
        switch (conf.getAttr('svcType')) {
            case 'ClusterIP':
                return svcTemplateClusterIP()
        }
    }

    private String getDeploymentTemplate() {
        if ( this.conf.getAttr('k8sKind') != 'deployment' ) return ''
        def text = '''
---
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: $appName
  namespace: $namespace
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: $appName
    spec:
      imagePullSecrets:
      - name: regsecret
      containers:
      - name: $appName
        image: $dockerRegistryHost/$namespace/$appName:$branchName-$buildNumber
        imagePullPolicy: Always #
$volumeMounts        
$command        
        ports:
        - containerPort: $containerPort
        resources:
          limits:
            cpu: $cpuLimits
            memory: $memoryLimits
          requests:
            cpu: $cpuRequests
            memory: $memoryRequests
$volumes                
'''
        def bind = [
                'appName'             : this.conf.appName,
                'namespace'           : this.conf.getAttr('namespace'),
                'dockerRegistryHost'  : conf.dockerRegistryHost,
                'branchName'          : conf.getAttr('branchName'),
                'buildNumber'         : conf.getAttr('buildNumber'),
                'containerPort'       : this.conf.getAttr('containerPort'),
                'cpuRequests'         : conf.getAttr('cpuRequests'),
                'memoryRequests'      : conf.getAttr('memoryRequests'),
                'cpuLimits'           : conf.getAttr('cpuLimits'),
                'memoryLimits'        : conf.getAttr('memoryLimits'),
                'volumeMounts '       : '1119',
                'command'             : this.getCommand(),
                'volumes'             : this.getVolumes()
        ]
        return Tools.simpleTemplate(text, bind)
    }

    private String svcTemplateClusterIP() {
        def text = '''
---
apiVersion: v1
kind: Service
metadata:
  labels:
    app: $appName
  name: $appName
  namespace: $namespace
spec:
  ports:
  - port: $containerPort
    protocol: TCP
    targetPort: $containerPort
  selector:
    app: $appName
  type: ClusterIP
'''
        def bind = [
                'appName' : this.conf.appName,
                'namespace' : this.conf.getAttr('namespace'),
                'containerPort' : this.conf.getAttr('containerPort'),
        ]

        return Tools.simpleTemplate(text, bind)
    }
    /*
    /
    / 用途：设置不同类型，镜像启动的时候，需要执行的命令
    */
    private String getCommand() {
        switch (conf.getAttr('codeLanguage')) {
            case 'prometheus-alertmanager':
                return '''
        command:
        - "/workspace/alertmanager/alertmanager"
        args:
        - "--config.file=/data/prometheus/alertmanager/alertmanager.yml"
'''
        }
    }

    String getVolumeMounts() {
        switch (this.conf.getAttr('codeLanguage')) {
            case 'prometheus-alertmanager':
                return '''11111'''
            default:
                return ' '
        }
    }

    private String getVolumes() {
        switch (conf.getAttr('codeLanguage')) {
            case 'prometheus-alertmanager':
                return ''' '''
            default:
                return ' '
        }
    }
}
