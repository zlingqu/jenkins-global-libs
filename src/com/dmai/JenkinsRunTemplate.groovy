package com.dmai

import com.tool.Tools

class JenkinsRunTemplate {
    private Conf conf

    JenkinsRunTemplate(Conf conf) {
        this.conf = conf
    }

    public String getJenkinsRunTemplate() {
        def returnString = this.templateTop() +
                this.templateDockerCompile() +
                this.templateDockerKubectl() +
                this.templateDockerKubectlTest() +
                this.customImage() +
                this.templateDockerCompose() +
                this.templateJsCompileVolumes()
        return returnString
    }

    private def templateTop() {
        return '''
apiVersion: v1
kind: Pod
metadata:
  name: jenkinsTemplate
  namespace: devops
spec:
  imagePullSecrets:
  - name: regsecret
  containers:
'''
    }

    private String templateDockerCompose() {
        if (! this.conf.getAttr('makeImage')) return ''
        return '''
  - name: docker-compose
    image: docker.dm-ai.cn/devops/base-image-docker-compose:0.04
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn  
    volumeMounts:
    - name: sock
      mountPath: /var/run/docker.sock
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
    resources:
      limits:
        memory: 1000Mi
        cpu: 800m
      requests:
        cpu: 400m
        memory: 600Mi
  volumes:
  - name: sock
    hostPath:
      path: /var/run/docker.sock
'''
    }

    private String templateJsCompileVolumes() {
        if (this.conf.getAttr('makeImage') && this.conf.getAttr('compile') && this.conf.getAttr('codeLanguage') in ['js']) {
            return String.format('''
  - name: node-modules
    hostPath:
      path: /data1/jenkins/cache/%s/%s
''',this.conf.getAttr('namespace'), this.conf.appName)
        }
        return ''
    }

    private String templateJsCompilevolumeMounts() {
        if (this.conf.getAttr('makeImage') && this.conf.getAttr('compile') && this.conf.getAttr('codeLanguage') in ['js']) {
            return String.format('''
    volumeMounts:
    - name: node-modules
      mountPath: /data/cache/node_modules
''')
        }
        return ''
    }

    private String templateDockerKubectl() {
        if (this.conf.getAttr('deploy')) {
            return  String.format('''
  - name: kubectl 
    image: docker.dm-ai.cn/devops/base-image-kubectl:%s-0.04
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
    resources:
      limits:
        memory: 300Mi
        cpu: 200m
      requests:
        cpu: 100m
        memory: 200Mi
''', this.getKubectlBranch())
        } else {
            return ''
        }
    }

//    设置不同的分支部署到不同的环境
    private String getKubectlBranch(){
        if (this.conf.getAttr('branchName') == 'master' ) {
            if (this.conf.getAttr('master') == 'prd') {
                return 'master'
            } else {
                return ''
            }
        }

        switch (this.conf.getAttr('dev')){
            case 'dev'  : return 'dev'
            case 'test' : return 'test'
            case 'master': return 'master'
        }
    }

//    private templateDockerKubectlStage() {
//        if (this.conf.getAttr('stage')) {
//            return '''
//  - name: kubectl-stage
//    image: docker.dm-ai.cn/devops/base-image-kubectl:stage-0.01
//    imagePullPolicy: IfNotPresent
//    env: #指定容器中的环境变量
//    - name: DMAI_PRIVATE_DOCKER_REGISTRY
//      value: docker.dm-ai.cn
//    command:
//    - "sleep"
//    args:
//    - "1200"
//    tty: true
//    resources:
//      limits:
//        memory: 300Mi
//        cpu: 200m
//      requests:
//        cpu: 100m
//        memory: 200Mi
//'''
//        }
//        return ''
//    }

    private templateDockerKubectlTest() {
        if (this.conf.getAttr('test')) {
            return '''
  - name: kubectl-test 
    image: docker.dm-ai.cn/devops/base-image-kubectl:test-0.04
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
    resources:
      limits:
        memory: 300Mi
        cpu: 200m
      requests:
        cpu: 100m
        memory: 200Mi
'''
        }
        return ''
    }

    private customImage() {
        if (this.conf.getAttr('useCustomImage')) {
            return String.format('''
  - name: custom-image
    image: %s
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
    resources:
      limits:
        memory: 3000Mi
        cpu: 2000m
      requests:
        cpu: 1000m
        memory: 2000Mi
''', this.conf.getAttr('customImage'))
        }
        return ''
    }

//    private templateDockerKubectlCloud() {
//        if (this.conf.getAttr('cloud')) {
//            return '''
//  - name: kubectl-cloud
//    image: docker.dm-ai.cn/devops/base-image-kubectl:cloud-0.01
//    imagePullPolicy: IfNotPresent
//    env: #指定容器中的环境变量
//    - name: DMAI_PRIVATE_DOCKER_REGISTRY
//      value: docker.dm-ai.cn
//    command:
//    - "sleep"
//    args:
//    - "1200"
//    tty: true
//    resources:
//      limits:
//        memory: 300Mi
//        cpu: 200m
//      requests:
//        cpu: 100m
//        memory: 200Mi
//'''
//        }
//        return ''
//    }

    private String templateDockerCompile() {
        if (! this.conf.getAttr('compile')) return ''
        switch (this.conf.getAttr('codeLanguage')) {
            case 'js':
                return String.format('''
  - name: compile
    image: docker.dm-ai.cn/devops/base-image-compile-frontend:0.03
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
%s
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
    resources:
      requests:
        cpu: 2000m
        memory: 4000Mi
''', this.templateJsCompilevolumeMounts())

            case 'c++':
                return '''
  - name: compile
    image: docker.dm-ai.cn/devops/media-access:r.04
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
    resources:
      limits:
        memory: 1500Mi
        cpu: 1000m
      requests:
        cpu: 500m
        memory: 1000Mi
'''
            default:
                return ''
        }
    }
}

