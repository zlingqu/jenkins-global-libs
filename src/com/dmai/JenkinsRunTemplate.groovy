package com.dmai

import com.tool.Tools

class JenkinsRunTemplate {
    private Conf conf
    private String deployMasterPassword

    JenkinsRunTemplate(Conf conf) {
        this.conf = conf
        this.deployMasterPassword = ''
    }

    public String getJenkinsRunTemplate(String deployMasterPassword, String deployEnvironment) {
        this.deployMasterPassword = deployMasterPassword
        this.conf.setAttr('dev', deployEnvironment)

//        set branchName , jobName, buildNumber
//        this.conf.setAttr('branchName', currentBuild.projectName)
//        this.conf.setAttr('jobName', currentBuild.fullProjectName.split("/")[0])
//        this.conf.setAttr('buildNumber', currentBuild.displayName.replaceAll("#", ""))

        def returnString = this.templateTop() +
                this.templateDockerCompile() +
                this.templateDockerKubectl() +
                this.templateDockerKubectlTest() +
                this.templateSonarCheck() +
                this.customImage() +
                this.templateDockerCompose() +
                this.templateJsCompileVolumes() +
                this.templateJavaCompileVolumes() +
                this.nodeSelect()
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
  hostAliases:
  - ip: 192.168.3.140
    hostnames:
    - sonar.ops.dm-ai.cn
  - ip: 192.168.3.221
    hostnames:
    - gitlab.dm-ai.cn
  - ip: 192.168.3.38
    hostnames:
    - npm.dm-ai.cn
  imagePullSecrets:
  - name: regsecret
  containers:
'''
    }

    private String nodeSelect() {
        if (this.conf.getAttr('envType') == 'gpu') {
            return '''
  nodeSelector:
    makeenv: gpu
'''
        }
        return '''
  nodeSelector:
    makeenv: cpu
'''
    }

    private String templateDockerCompose() {
        if (! this.conf.getAttr('makeImage')) return ''
        return String.format('''
  - name: docker-compose
    image: docker.dm-ai.cn/devops/base-image-docker-compose:0.05
    imagePullPolicy: IfNotPresent
    env:
    - name: VUE_APP_SCENE
      value: %s
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn  
    volumeMounts:
    - name: sock
      mountPath: /var/run/docker.sock
    command:
    - "sleep"
    args:
    - "2400"
    tty: true
  volumes:
  - name: sock
    hostPath:
      path: /var/run/docker.sock
''', this.conf.vueAppScene)
    }

    private String templateJsCompileVolumes() {
        if (this.conf.getAttr('makeImage') && this.conf.getAttr('codeLanguage') in ['js', 'node']) {
            return String.format('''
  - name: node-modules
    persistentVolumeClaim:
      claimName: jenkins-pvc
''',this.conf.getAttr('namespace'), this.conf.appName)
        }
        return ''
    }

    private String templateJavaCompileVolumes() {
        if ( this.conf.getAttr('compile') && this.conf.getAttr('codeLanguage') == 'java') {
            return String.format('''
%s
  - name: data1
    hostPath:
      path: /data1/jenkins/%s/%s
''', this.conf.getAttr('makeImage') ? '' : '  volumes:', this.conf.getAttr('namespace'), this.conf.appName)
        }
        return ''
    }

    private String templateJsCompilevolumeMounts() {
        if (this.conf.getAttr('makeImage') && this.conf.getAttr('codeLanguage') in ['js', 'node']) {
            return String.format('''
    volumeMounts:
    - name: node-modules
      mountPath: /data/cache/node_modules
      subPath: jenkins_home/node_cache/%s/%s
''', this.conf.appName, this.conf.getAttr('branchName'))
        }
        return ''
    }

    private String templateDockerKubectl() {
        if (this.conf.getAttr('branchName') == 'master' && this.deployMasterPassword != 'dmai2019') return ''

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
    - "2400"
    tty: true
''', this.getKubectlBranch())
        } else {
            return ''
        }
    }

    private String templateSonarCheck() {
        return String.format('''
  - name: sonar-check
    image: docker.dm-ai.cn/devops/sonar-scanner:4.0
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    command:
    - "sleep"
    args:
    - "2400"
    tty: true
''')
    }

//    设置不同的分支部署到不同的环境
    private String getKubectlBranch(){
        if (this.conf.getAttr('branchName') == 'stage') return 'stage'
        if (this.conf.appName in  [ 'xmc-online-api', 'xmc-detection-api', 'xmc-gesture-api', 'xmc-holdobj-api', 'facial-expression-cls' ]) return 'test'
        if (this.conf.getAttr('branchName') == 'master' ) {
            if (this.conf.getAttr('master') == 'prd' && this.deployMasterPassword == 'dmai2019') {
                return 'master'
            } else {
                return ''
            }
        }

        switch (this.conf.getAttr('dev')){
            case 'dev'  : return 'dev'
            case 'test' : return 'test'
            case 'lexue' : return 'lexue'
//            case 'master': return 'master'
        }

        throw "dev分支，目前只能部署dev/test/lexue环境，其他的均为异常情况"
//        return ''
    }


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
    - "2400"
    tty: true
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
    - "2400"
    tty: true
''', this.conf.getAttr('customImage'))
        }
        return ''
    }


    private String templateDockerCompile() {
//        if (! this.conf.getAttr('compile')) return ''
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
    - "2400"
    tty: true
''', this.templateJsCompilevolumeMounts())

            case 'node':
                return String.format('''
  - name: compile
    image: docker.dm-ai.cn/devops/node:0.0.2
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
%s
    command:
    - "sleep"
    args:
    - "2400"
    tty: true
''', this.templateJsCompilevolumeMounts())

            case 'c++':
                return '''
  - name: compile
    image: docker.dm-ai.cn/devops/media-access:r.13
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    command:
    - "sleep"
    args:
    - "2400"
    tty: true
'''
            case 'java':
                return '''
  - name: compile
    image: docker.dm-ai.cn/devops/base-image-mvn:0.01
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    volumeMounts:
    - name: data1
      mountPath: /root/.m2
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
'''
            default:
                return ''
        }
    }
}

