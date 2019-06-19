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
                return this.svcTemplateClusterIP()
            case 'NodePort':
                return this.svcTemplateNodePort()
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
  replicas: $replicas
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
        env: #指定容器中的环境变量
        - name: TZ
          value: Asia/Shanghai        
$volumeMounts
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
                'volumeMounts'        : this.getVolumeMounts(),
                'volumes'             : this.getVolumes(),
                'replicas'            : this.conf.getAttr('replicas') ? this.conf.getAttr('replicas') : 1
        ]
        return Tools.simpleTemplate(text, bind)
    }

    private String svcTemplateNodePort() {
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
    nodePort: $nodePort
  selector:
    app: $appName
  type: NodePort
'''
        def bind = [
                'appName' : this.conf.appName,
                'namespace' : this.conf.getAttr('namespace'),
                'containerPort' : this.conf.getAttr('containerPort'),
                'nodePort' : this.conf.getAttr('nodePort')
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

    private String getVolumeMounts() {
        switch (this.conf.getAttr('codeLanguage')) {
            case 'node':
                return this.getVolumeMountsNode()
            default:
                return ''
        }
    }

    private String getVolumes() {
        switch (conf.getAttr('codeLanguage')) {
            case 'node':
                return this.getVolumesNode()
            default:
                return ''
        }
    }


    private String getVolumesNode() {
        return String.format('''
      volumes:
      - name: %s
        configMap:
          name: %s
      - name: data
%s
''', this.conf.appName, this.conf.appName, this.getDataMode())
    }

    private String getDataMode() {
        switch (this.conf.getAttr('branchName')) {
            case 'master':
                return '''
        persistentVolumeClaim:
          claimName: mypvc
'''
            default:
                return String.format('''
        hostPath:
           path: /data/%s%s
''', this.conf.getAttr('namespace'), this.conf.appName in ['storage-service', 'stat-service'] ? '' : '/' + this.conf.appName)
        }
    }

    private String getVolumeMountsNode() {
        return String.format('''
        volumeMounts:
        - name: %s
          mountPath: /app/%s
          subPath: %s
        - name: data
          mountPath: /app/data
''', this.conf.appName, this.conf.getAttr('configMapName'), this.conf.getAttr('configMapName'))
    }
}
