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
        $command
        env: #指定容器中的环境变量
        - name: TZ
          value: Asia/Shanghai        
$volumeMounts
        ports:
        - containerPort: $containerPort
        $getContainerPort
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
                'replicas'            : this.conf.getAttr('replicas') ? this.conf.getAttr('replicas') : 1,
                'command'             : this.conf.getAttr('command') ? this.conf.getAttr('command'): '',
                'getContainerPort'    : this.getContainerPort()
        ]
        return Tools.simpleTemplate(text, bind)
    }

    private String getContainerPort() {
        def returnString = ''
        if (this.conf.getAttr('udpPort')) {
            for (int i in this.conf.getAttr('udpPort')[0]..this.conf.getAttr('udpPort')[1]) {
                returnString += String.format('''
        - containerPort: %s
''', i)
            }
        }

        return returnString
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
  - port: $servicePort
    protocol: TCP
    targetPort: $containerPort
    nodePort: $nodePort
    name: $appName-$containerPort
$getUdpSvc    
  selector:
    app: $appName
  type: NodePort
'''
        def bind = [
                'appName' : this.conf.appName,
                'namespace' : this.conf.getAttr('namespace'),
                'containerPort' : this.conf.getAttr('containerPort'),
                'nodePort' : this.conf.getAttr('nodePort'),
                'getUdpSvc': this.getUdpSvc(),
                'servicePort': this.conf.getAttr('servicePort')
        ]

        return Tools.simpleTemplate(text, bind)
    }

    private String getUdpSvc() {
        def returnString = ''
        if (this.conf.getAttr('udpPort')) {
            for (int i in this.conf.getAttr('udpPort')[0]..this.conf.getAttr('udpPort')[1]) {
                returnString += String.format('''
  - port: %s
    protocol: UDP
    targetPort: %s
    nodePort: %s
    name: %s-%s
''', i, i, i, this.conf.appName, i)
            }
        }
        return returnString
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
                return this.getVolumeMountsString()
            case 'python':
                return this.getVolumeMountsString()
            case 'c++':
                return this.getVolumeMountsString()
            default:
                return ''
        }
    }

    private String getVolumes() {
        switch (conf.getAttr('codeLanguage')) {
            case 'node':
                return this.getVolumesString()
            case 'python':
                return this.getVolumesString()
            case 'c++':
                return this.getVolumesString()
            default:
                return ''
        }
    }


    private String getVolumesString() {
        if (this.conf.appName in ['media-gateway', 'media-access']) {
            return String.format('''
      volumes:
      - name: config
        configMap:
          name: %s
          items:
          - key: config.json
            path: config.json
      - name: log
        configMap:
          name: %s
          items:
          - key: log.conf
            path: log.conf           
''', this.conf.appName, this.conf.appName)
        }

//        非特殊情况下
        def returnString = '''
      volumes:
'''
        if (this.conf.getAttr('useConfigMap')) {
            returnString += String.format('''
      - name: %s
        configMap:
          name: %s
''', this.conf.appName, this.conf.appName)
        }

        if (this.conf.getAttr('usePvc')) {
            returnString += String.format('''
      - name: data
        persistentVolumeClaim:
          claimName: mypvc
''')
        }
        return  returnString
//        return String.format('''
//      volumes:
//      - name: %s
//        configMap:
//          name: %s
//      - name: data
//%s
//''', this.conf.appName, this.conf.appName, this.getDataMode())
    }

//    private String getDataMode() {
//        switch (this.conf.getAttr('branchName')) {
//            case 'master':
//                return '''
//        persistentVolumeClaim:
//          claimName: mypvc
//'''
//            case 'dev':
//                return '''
//        persistentVolumeClaim:
//          claimName: mypvc
//'''
//            default:
//                return String.format('''
//        hostPath:
//           path: /data/%s%s
//''', this.conf.getAttr('namespace'), this.conf.appName in ['vod-service', 'ui-backend-service', 'storage-service', 'stat-service', 'dispatcher-service', 'dispatcher-service'] ? '' : '/' + this.conf.appName)
//        }
//    }

    private String getVolumeMountsString() {
        if (this.conf.appName in ['media-gateway', 'media-access']) {
            return '''
        volumeMounts:
        - name: config
          mountPath: /src/debug/config.json
          subPath: config.json
        - name: log
          mountPath: /src/debug/log.conf
          subPath: log.conf
'''
        }

//        非特殊情况下：
        def returnString = '''
        volumeMounts:
'''
        if (this.conf.getAttr('useConfigMap')) {
            returnString += String.format('''
        - name: %s
          mountPath: /app/%s
          subPath: %s
''', this.conf.appName, this.conf.getAttr('configMapName'), this.conf.getAttr('configMapName'))
        }

        if (this.conf.getAttr('usePvc')) {
            returnString += String.format('''
        - name: data
          mountPath: /app/data
''')
        }
        return returnString
//        return String.format('''
//        volumeMounts:
//        - name: %s
//          mountPath: /app/%s
//          subPath: %s
//        - name: data
//          mountPath: /app/data
//''', this.conf.appName, this.conf.getAttr('configMapName'), this.conf.getAttr('configMapName'))
    }
}
