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
        if (!this.conf.getAttr('useService')) return ''

        def svcType = conf.getAttr('svcType')
        if (this.conf.getAttr('branchName') == 'master') {
            svcType = 'ClusterIP'
        }

        switch (svcType) {
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
apiVersion: apps/v1
kind: Deployment
metadata:
  name: $appName
  namespace: $namespace
spec:
  replicas: $replicas
  selector:
    matchLabels:
        app: $appName
  template:
    metadata:
      labels:
        app: $appName
    spec:
      imagePullSecrets:
      - name: regsecret
$tolerations
      containers:
      - name: $appName
        image: $dockerRegistryHost/$namespace/$appName:$branchName-$buildNumber
        imagePullPolicy: Always #
        $command
        $envFrom
        env: #指定容器中的环境变量
        - name: TZ
          value: Asia/Shanghai        
$volumeMounts
        ports:
        - containerPort: $containerPort
        $getContainerPort
$resources
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
                'getContainerPort'    : this.getContainerPort(),
                'resources'           : this.resourcesTemplate(),
                'envFrom'             : this.getEnvFrom(),
                'tolerations'         : this.getTolerations()
        ]
        return Tools.simpleTemplate(text, bind)
    }

    private String getTolerations() {
        // tmp
        if (this.conf.appName in  ['xmc-online-api', 'xmc-detection-api', 'xmc-gesture-api', 'xmc-holdobj-api', 'facial-expression-cls']) {
            return '''
      nodeSelector:
        tools: zhengwenyong
      tolerations:
      - key: "hardware"
        operator: "Equal"
        value: "gpu"
        effect: "NoSchedule"
      - key: "tools"
        operator: "Equal"
        value: "zhengwenyong"
        effect: "NoSchedule"
'''
        }
        //
        if (this.conf.getAttr('envType') == 'all') {
            return '''
      tolerations:
      - key: "hardware"
        operator: "Equal"
        value: "gpu"
        effect: "NoSchedule"
'''
        }
        //
        if (this.conf.getAttr('envType') == 'gpu') {
            return '''
      nodeSelector:
        gpu: enable
      tolerations:
      - key: "hardware"
        operator: "Equal"
        value: "gpu"
        effect: "NoSchedule"
'''
        }
        if (this.conf.getAttr('dev') == 'lexue' && this.conf.appName in ['vod-service', 'storage-service'])  {
            return '''
      nodeSelector:
        env: storage
'''
        }
        return ''
    }

    // 根据用户的设置来选择是否，使用批量的环境变量的注入方式：
    private String getEnvFrom() {
        if (this.conf.getAttr('useEnvFile') && this.conf.getAttr('useConfigMap')) {
            return String.format('''
        envFrom:
        - configMapRef:
            name: %s
''', this.conf.appName)
        }
        return ''
    }


    // 根据用户的设置，来生成yaml中，是否对资源限制的模版文件。
    private String resourcesTemplate() {
        def returnString = ''
        def topString = '''
        resources:
'''
        for (attr in ['cpuRequests', 'memoryRequests', 'cpuLimits', 'memoryLimits', 'gpuLimits']){
            if (this.conf.getAttr(attr) && this.conf.getAttr(attr) != '') {
                returnString += topString
                break
            }
        }

        for (requestAttr in ['cpuRequests', 'memoryRequests']) {
            if (this.conf.getAttr(requestAttr) && this.conf.getAttr(requestAttr) != '') {
                returnString += '''
          requests:
'''
                break
            }
        }

        if (this.conf.getAttr('cpuRequests') && this.conf.getAttr('cpuRequests') != '') {
            returnString += String.format('''
            cpu: %s
''', this.conf.getAttr('cpuRequests'))
        }

        if (this.conf.getAttr('memoryRequests') && this.conf.getAttr('memoryRequests') != '') {
            returnString += String.format('''
            memory: %s
''', this.conf.getAttr('memoryRequests'))
        }

        for (limitsAttr in ['cpuLimits', 'memoryLimits', 'gpuLimits']) {
            if (this.conf.getAttr(limitsAttr) && this.conf.getAttr(limitsAttr) != '') {
                returnString += '''
          limits:
'''
                break
            }
        }

        if (this.conf.getAttr('cpuLimits') && this.conf.getAttr('cpuLimits') != '') {
            returnString += String.format('''
            cpu: %s
''', this.conf.getAttr('cpuLimits'))
        }

        if (this.conf.getAttr('memoryLimits') && this.conf.getAttr('memoryLimits') != '') {
            returnString += String.format('''
            memory: %s
''', this.conf.getAttr('memoryLimits'))
        }

        if (this.conf.getAttr('gpuLimits') && this.conf.getAttr('gpuLimits') != '') {
            returnString += String.format('''
            nvidia.com/gpu: %s
''', this.conf.getAttr('gpuLimits'))
        }

        returnString
    }

    private String getContainerPort() {
        def returnString = ''
        if (this.conf.getAttr('tcpPort')) {
            for (int i in this.conf.getAttr('tcpPort')[0]..this.conf.getAttr('tcpPort')[1]) {
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
$getTcpSvc    
  selector:
    app: $appName
  type: NodePort
'''
        def bind = [
                'appName' : this.conf.appName,
                'namespace' : this.conf.getAttr('namespace'),
                'containerPort' : this.conf.getAttr('containerPort'),
                'nodePort' : this.conf.getAttr('nodePort'),
                'getTcpSvc': this.getTcpSvc(),
                'servicePort': this.conf.getAttr('servicePort')
        ]

        return Tools.simpleTemplate(text, bind)
    }

    private String getTcpSvc() {
        def returnString = ''
        if (this.conf.getAttr('tcpPort')) {
            for (int i in this.conf.getAttr('tcpPort')[0]..this.conf.getAttr('tcpPort')[1]) {
                returnString += String.format('''
  - port: %s
    protocol: TCP
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
  - port: $servicePort
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
                'servicePort'   : this.conf.getAttr('servicePort'),
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
            if (this.conf.getAttr('dev') == 'lexue') {
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
      - name: data
        hostPath:
          path: /data/%s
''', this.conf.appName, this.conf.appName, this.conf.getAttr('namespace'))
            }
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
      - name: data
        persistentVolumeClaim:
          claimName: mypvc
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

        if (this.conf.getAttr('useStore')) {
//
            if (this.conf.getAttr('dev') == 'lexue') {
                returnString += String.format('''
      - name: data
        hostPath:
          path: /data/%s
''', this.conf.getAttr('namespace'))
                return  returnString
            }
//
            returnString += String.format('''
      - name: data
        persistentVolumeClaim:
          claimName: mypvc
''')
        }
        return  returnString
    }

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
        - name: data
          mountPath: /data
'''
        }

        if (this.conf.getAttr('configMapName') in [null, '', false] && this.conf.getAttr('useStore') in [null, '', false]) return ''

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

        if (this.conf.getAttr('useStore')) {
            returnString += String.format('''
        - name: data
          mountPath: %s
''', this.conf.getAttr('storePath') ? this.conf.getAttr('storePath') : '/data')
        }
        return returnString
    }
}
