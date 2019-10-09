package com.dmai

import com.tool.Tools

class JenkinsRunTemplate {
    private Conf conf
    private String deployMasterPassword

    JenkinsRunTemplate(Conf conf) {
        this.conf = conf
        this.deployMasterPassword = ''
    }

    private void setConfInitPara(params) {
        // 自定义appName
        if (this.conf.appName == 'xmc-xc-model-serving') {
            this.conf.setAppName(params.APP_NAME)
            if (this.conf.appName == 'xmc-xc-model-serving') {
                throw "请修改APP_NAME"
            }
            this.conf.setUserAttr(new GlobalConfig().globalConfig.get(this.conf.appName))
        } else if (this.conf.appName == 'xmc2-frontend') {
            this.conf.setAppName(this.conf.appName + (params.VUE_APP_SCENE == 'school' ? '' : '-' + params.VUE_APP_SCENE) + (params.VUE_APP_SCHOOL == 'S00001' ? '' : '-' + params.VUE_APP_SCHOOL)  )
            this.conf.setVueAppScene(params.VUE_APP_SCENE)
            this.conf.setVueAppSchool(params.VUE_APP_SCHOOL)
        } else {
            if (params.APP_NAME != 'xmc-xc-model-serving' && params.APP_NAME != 'xmc-model-serving-student') {
                this.conf.setAppName(params.APP_NAME)
            }
        }

//        if (this.conf.appName == 'xmc2-frontend') {
//            this.conf.setAppName(this.conf.appName + (params.VUE_APP_SCENE == 'school' ? '' : '-' + params.VUE_APP_SCENE) + (params.VUE_APP_SCHOOL == 'S00001' ? '' : '-' + params.VUE_APP_SCHOOL)  )
//            this.conf.setVueAppScene(params.VUE_APP_SCENE)
//            this.conf.setVueAppSchool(params.VUE_APP_SCHOOL)
//
//        }

        this.conf.setAttr('replicas', params.REPLICAS)

        this.conf.setAttr('envType', params.ENV_TYPE)

        // set GPU_CARD_COUNT
        this.conf.setAttr('gpuLimits', params.GPU_CARD_COUNT)

        if (this.conf.getAttr('deployEnv') == 'lexue') {
            this.conf.setockerRegistryHost('rdac-docker.dm-ai.cn')
        }

        this.conf.setAttr('cpuRequests', params.CPU_REQUEST)
        this.conf.setAttr('memoryRequests', params.MEMORY_REQUEST)
        this.conf.setAttr('cpuLimits', params.CPU_LIMIT)
        this.conf.setAttr('memoryLimits', params.MEMORY_LIMIT)

        // 算饭专用
        this.conf.setModelVersion(params.MODEL_VERSION)

        // 前端专用
        this.conf.setAttr('nodeEnv', params.NODE_ENV)

        // set android param
        this.conf.setAttr('compileParam', params.COMPILE_PARAM)

        // set name spaces
        this.conf.setAttr('namespace', params.NAMESPACE)
        // 针对特殊情况
        if (this.conf.getAttr('namespace') in ['xmc2-lexue', 'xmc2-chongwen']) {
            this.conf.setAttr('svcType', 'ClusterIP')
        }

        // set git address
        this.conf.setAttr('gitAddress', params.GIT_ADDRESS)

        // set compile
        this.conf.setAttr('compile', params.COMPILE)

        // set code language
        this.conf.setAttr('codeLanguage', params.CODE_LANGUAGE)

        // set deploy
        this.conf.setAttr('deploy', params.DEPLOY)

        // set domain
        this.conf.setAttr('domain', params.DOMAIN)

        // set CUSTOM_KUBERNETES_DEPLOY_TEMPLATE
        this.conf.setAttr('customKubernetesDeployTemplate', params.CUSTOM_KUBERNETES_DEPLOY_TEMPLATE)

        // set CUSTOM_KUBERNETES_DEPLOY_TEMPLATE_CONTENT
        this.conf.setAttr('autoDeployContent', params.CUSTOM_KUBERNETES_DEPLOY_TEMPLATE_CONTENT)

        // set CUSTOM_DOCKERFILE
        this.conf.setAttr('customDockerfile', params.CUSTOM_DOCKERFILE)

        // set CUSTOM_DOCKERFILE_CONTENT
        this.conf.setAttr('customDockerfileContent', params.CUSTOM_DOCKERFILE_CONTENT)
    }

    public String getJenkinsRunTemplate(String deployMasterPassword, String deployEnvironment, params) {
        this.deployMasterPassword = deployMasterPassword
        this.conf.setAttr('deployEnv', deployEnvironment)
        this.setConfInitPara(params)

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
//                this.templateJsCompileVolumes() +
//                this.templateJavaCompileVolumes() +
//                this.templateAndroidCompileVolumes() +
                this.defaultVolumes() +
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
  - ip: 192.168.69.201
    hostnames:
    - docker.dm-ai.cn
  - ip: 192.168.3.38
    hostnames:
    - pip.dm-ai.cn
  imagePullSecrets:
  - name: regsecret
  containers:
'''
    }

    private String nodeSelect() {
        if (this.conf.getAttr('buildEnvType') == 'gpu') {
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
%s
    command:
    - "sleep"
    args:
    - "3600"
    tty: true
''', this.conf.vueAppScene, this.useModelPath())
    }

    private String useModelPath() {
        if (this.conf.getAttr('useModel') && this.conf.getAttr('modelPath')) {
            return String.format('''
    - name: jenkins-build-path
      mountPath: /models
      subPath: models/%s/%s
''', this.conf.appName, this.conf.getAttr('deployEnv'))
        }
        return ''
    }

    private String defaultVolumes() {
        return String.format('''
  volumes:
  - name: sock
    hostPath:
      path: /var/run/docker.sock
  - name: jenkins-build-path
    persistentVolumeClaim:
      claimName: jenkins-pvc
''')
    }

//    private String templateJsCompileVolumes() {
//        if (this.conf.getAttr('makeImage') && this.conf.getAttr('codeLanguage') in ['js', 'node']) {
//            return String.format('''
//  - name: jenkins-build-path
//    persistentVolumeClaim:
//      claimName: jenkins-pvc
//''',this.conf.getAttr('namespace'), this.conf.appName)
//        }
//        return ''
//    }

//    private String templateJavaCompileVolumes() {
//        if ( this.conf.getAttr('compile') && this.conf.getAttr('codeLanguage') == 'java') {
//            return String.format('''
//%s
//  - name: jenkins-build-path
//    persistentVolumeClaim:
//      claimName: jenkins-pvc
//''', this.conf.getAttr('makeImage') ? '' : '  volumes:', this.conf.getAttr('namespace'), this.conf.appName)
//        }
//        return ''
//    }

//    private String templateAndroidCompileVolumes() {
//        if (this.conf.getAttr('compile') && this.conf.getAttr('codeLanguage') == 'android') {
//            return String.format('''
//%s
//  - name: jenkins-build-path
//    persistentVolumeClaim:
//      claimName: jenkins-pvc
//''', this.conf.getAttr('makeImage') ? '' : '  volumes:')
//        }
//        return ''
//    }

    private String templateJsCompilevolumeMounts() {
        if (this.conf.getAttr('makeImage') && this.conf.getAttr('codeLanguage') in ['js', 'node']) {
            return String.format('''
    volumeMounts:
    - name: jenkins-build-path
      mountPath: /data/cache/node_modules
      subPath: jenkins_home/node_cache/%s/%s
''', this.conf.appName, this.conf.getAttr('branchName'))
        }
        return ''
    }

    private String templateDockerKubectl() {
        if (this.conf.getAttr('branchName') == 'master' && this.deployMasterPassword != 'dmai2019999') return ''

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
    - "3600"
    tty: true
''', this.conf.getAttr('deployEnv'))
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
    - "3600"
    tty: true
''')
    }

//    设置不同的分支部署到不同的环境
//    private String getKubectlBranch(){
//        if (this.conf.getAttr('branchName') == 'stage') return 'stage'
//        if (this.conf.appName in  [ 'xmc-online-api', 'xmc-detection-api', 'xmc-gesture-api', 'xmc-holdobj-api', 'facial-expression-cls' ]) return 'test'
//        if (this.conf.getAttr('branchName') == 'master' ) {
//            if (this.conf.getAttr('master') == 'prd' && this.deployMasterPassword == 'dmai2019999') {
//                return 'master'
//            } else {
//                return ''
//            }
//        }

//        switch (this.conf.getAttr('dev')){
//            case 'dev'  : return 'dev'
//            case 'test' : return 'test'
//            case 'lexue' : return 'lexue'
////            case 'master': return 'master'
//        }
//
//        throw "dev分支，目前只能部署dev/test/lexue环境，其他的均为异常情况"
////        return ''
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
    - "3600"
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
    - "3600"
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
    - "3600"
    tty: true
''', this.templateJsCompilevolumeMounts())

            case 'android': return String.format('''
  - name: compile
    image: docker.dm-ai.cn/devops/dm-android:0.5
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    volumeMounts:
    - name: jenkins-build-path
      mountPath: /data
      subPath: android_home/%s/%s
    command:
    - "sleep"
    args:
    - "3600"
    tty: true
''', this.conf.appName, this.conf.getAttr('branchName'))

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
    - "3600"
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
    - "3600"
    tty: true
'''
            case 'java':
                return String.format('''
  - name: compile
    image: docker.dm-ai.cn/devops/base-image-mvn:0.01
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    volumeMounts:
    - name: jenkins-build-path
      mountPath: /root/.m2
      subPath: java_home/%s
    command:
    - "sleep"
    args:
    - "1200"
    tty: true
''', this.conf.appName)
            default:
                return ''
        }
    }
}

