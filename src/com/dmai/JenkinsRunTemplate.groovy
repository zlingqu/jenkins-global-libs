package com.dmai

import com.tool.Tools

class JenkinsRunTemplate {

  private Conf conf
  private String deployMasterPassword
  private String adpDate

  JenkinsRunTemplate(Conf conf) {
    this.conf = conf
    this.deployMasterPassword = ''
    this.adpDate = new Date().format('yyyyMMdd-HHmmss')
  }

  private void setConfInitPara(params) {
    // 自定义appName
    if (this.conf.appName in ['xmc-xc-model-serving']) {
      this.conf.setAppName(params.APP_NAME)
      this.conf.setAttr('appName', params.APP_NAME).toLowerCase()
      this.conf.setAttr('jobName', params.APP_NAME).toLowerCase()
      if (this.conf.appName in ['xmc-xc-model-serving']) {
        throw '请修改APP_NAME'
      }
      this.conf.setUserAttr(new GlobalConfig().globalConfig.get(this.conf.appName))
        } else if (this.conf.appName == 'xmc2-frontend') {
      this.conf.setAppName(this.conf.appName)
      this.conf.setVueAppScene(params.VUE_APP_SCENE)
        } else {
      if (params.APP_NAME != 'xmc-xc-model-serving' && params.APP_NAME != 'xmc-model-serving-student') {
        this.conf.setAppName(params.APP_NAME)
      }
    }

    this.conf.setAttr('replicas', params.REPLICAS)

    this.conf.setAttr('envType', params.ENV_TYPE)

    // set GPU_CONTROL_MODE
    this.conf.setAttr('gpuControlMode', params.GPU_CONTROL_MODE)

    // set GPU_MEM_COUNT
    this.conf.setAttr('gpuMemLimits', params.GPU_MEM_COUNT)

    // set GPU_CARD_COUNT
    this.conf.setAttr('gpuLimits', params.GPU_CARD_COUNT)

    
    this.conf.setAttr('gpuType', params.GPU_TYPE)

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

    // set https
    this.conf.setAttr('https', params.IF_USE_HTTPS)

    // set http
    this.conf.setAttr('http', params.IF_USE_HTTP)

    // set CUSTOM_KUBERNETES_DEPLOY_TEMPLATE
    this.conf.setAttr('customKubernetesDeployTemplate', params.CUSTOM_KUBERNETES_DEPLOY_TEMPLATE)

    // set CUSTOM_KUBERNETES_DEPLOY_TEMPLATE_CONTENT
    this.conf.setAttr('autoDeployContent', params.CUSTOM_KUBERNETES_DEPLOY_TEMPLATE_CONTENT)

    // set CUSTOM_DOCKERFILE
    this.conf.setAttr('customDockerfile', params.CUSTOM_DOCKERFILE)

    // set CUSTOM_DOCKERFILE_CONTENT
    this.conf.setAttr('customDockerfileContent', params.CUSTOM_DOCKERFILE_CONTENT)

    // set SERVICE_TYPE
    this.conf.setAttr('svcType', params.SERVICE_TYPE)

    // set replicationControllerType
    this.conf.setAttr('replicationControllerType', params.replicationControllerType)

    // set default port
    this.conf.setAttr('containerPort', params.CONTAINER_PORT)
    if (!this.conf.getAttr('containerPort')) {
      this.conf.setAttr('containerPort', '80')
    }

    // if make images
    this.conf.setAttr('makeImage', params.IF_MAKE_IMAGE)

    // set IF_CHECK_PODS_STATUS
    this.conf.setAttr('checkPodsStatus', params.IF_CHECK_PODS_STATUS)

    // set USE_MODEL
    this.conf.setAttr('useModel', params.USE_MODEL)

    // set USE_CONFIGMAP
    this.conf.setAttr('useConfigMap', params.USE_CONFIGMAP)

    // set IF_STORAGE_LOCALE
    this.conf.setAttr('useStore', params.IF_STORAGE_LOCALE)

    // set STORAGE_PATH
    this.conf.setAttr('storePath', params.STORAGE_PATH)

    // VERSION_CONTROL_MODE
    this.conf.setAttr('versionControlMode', params.VERSION_CONTROL_MODE)

    // GIT_TAG
    this.conf.setAttr('gitTag', params.GIT_TAG)

    // APOLLO_CLUSTER_NAME
    this.conf.setAttr('apolloClusterName', params.APOLLO_CLUSTER_NAME)

    // APOLLO_NAMESPACE
    this.conf.setAttr('apolloNamespace', params.APOLLO_NAMESPACE)

    // JS_VERSION
    this.conf.setAttr('jsVersion', params.JS_VERSION)

    // GIT_VERSION
    this.conf.setAttr('gitVersion', params.GIT_VERSION)

    //BRANCH_NAME
    this.conf.setAttr('jenkinsBranchName', params.BRANCH_NAME)
    this.conf.setAttr('branchName', Tools.handleBranchName(params.BRANCH_NAME))

    // DEPLOY_MASTER_PASSWORD
    this.conf.setAttr('deployPassword', params.DEPLOY_MASTER_PASSWORD)

    //BUILD_PLATFORM
    this.conf.setAttr('buildPlatform', params.BUILD_PLATFORM)

    // workspace
    this.conf.setAttr('configFilePath', '/app')
    if (this.conf.getAttr('jobName') in ['media-access', 'media-gateway']) {
      this.conf.setAttr('configFilePath', '/src/debug')
    }

    ///////////// 针对特殊情况
    if (this.conf.getAttr('namespace') in ['xmc2-lexue', 'xmc2-chongwen']) {
      this.conf.setAttr('svcType', 'ClusterIP')
    }

    // 设置全局的默认构建结果为失败
    this.conf.setAttr('buildResult', 'failure')

    if (this.conf.getAttr('useModel') && !this.conf.getAttr('modelPath') && !this.conf.getAttr('modelGitAddress')) {
      this.conf.setAttr('modelPath', 'app/data')
    }

    // 特殊设置默认的useEnvFile
    if (this.conf.getAttr('useConfigMap') && !this.conf.getAttr('configMapName')) {
      this.conf.setAttr('useEnvFile', true)
    }

    this.conf.setAttr('jenkinsJobName', this.conf.getAttr('jobName'))
    this.conf.setAttr('jobName', this.conf.getAttr('jobName').toLowerCase())

    // GLOABL_STRING
    this.conf.setAttr('useGrpc', false)
    this.conf.setAttr('useSticky', false)
    this.conf.setAttr('replicationControllerType', 'Deployment')
    this.conf.setAttr('if_add_unity_project', false)
    this.conf.setAttr('unity_app_name', 'no_unity')
    this.conf.setAttr('ifUseRootDockerfile', false)
    this.conf.setAttr('ifCompileParam', false)
    this.conf.setAttr('ifCompileImage', false)
    this.conf.setAttr('compileImage', '')
    this.conf.setAttr('ifUseGbs', false)
    this.conf.setAttr('ifCompileCache', false)
    this.conf.setAttr('ifUseModel', false)
    this.conf.setAttr('ifUseGitManagerModel', false)
    this.conf.setAttr('ifSaveModelBuildComputer', false)
    this.conf.setAttr('modelGitRepository', '')
    this.conf.setAttr('deployEnvStatus', 'start')
    this.conf.setAttr('ifUseIstio', false)
    this.conf.setAttr('ifUseApolloOfflineEnv', false)
    this.conf.setAttr('yamlEnv', 'None')
    this.conf.setAttr('androidFlavor', 'default')
    if (params.GLOABL_STRING != '') {
      def tmpStringList = params.GLOABL_STRING.split(':::')
      tmpStringList.length >= 1 ? this.conf.setAttr('useGrpc', tmpStringList[0]) : this.conf.setAttr('useGrpc', false)
      tmpStringList.length >= 2 ? this.conf.setAttr('useSticky', tmpStringList[1]) : this.conf.setAttr('useSticky', false)
      tmpStringList.length >= 3 ? this.conf.setAttr('replicationControllerType', tmpStringList[2]) : this.conf.setAttr('replicationControllerType', 'Deployment')
      tmpStringList.length >= 4 ? this.conf.setAttr('if_add_unity_project', tmpStringList[3]) : this.conf.setAttr('if_add_unity_project', false)
      tmpStringList.length >= 5 ? this.conf.setAttr('unity_app_name', tmpStringList[4]) : this.conf.setAttr('unity_app_name', 'no_unity')
      tmpStringList.length >= 6 ? this.conf.setAttr('ifUseRootDockerfile', Boolean.parseBoolean(tmpStringList[5])) : this.conf.setAttr('ifUseRootDockerfile', false)
      tmpStringList.length >= 7 ? this.conf.setAttr('ifCompileParam', Boolean.parseBoolean(tmpStringList[6])) : this.conf.setAttr('ifCompileParam', false)
      tmpStringList.length >= 8 ? this.conf.setAttr('ifCompileImage', Boolean.parseBoolean(tmpStringList[7])) : this.conf.setAttr('ifCompileImage', false)
      tmpStringList.length >= 9 ? this.conf.setAttr('compileImage', tmpStringList[8]) : this.conf.setAttr('compileImage', '')
      tmpStringList.length >= 10 ? this.conf.setAttr('ifUseGbs', Boolean.parseBoolean(tmpStringList[9])) : this.conf.setAttr('ifUseGbs', false)
      tmpStringList.length >= 11 ? this.conf.setAttr('ifCompileCache', Boolean.parseBoolean(tmpStringList[10])) : this.conf.setAttr('ifCompileCache', false)
      tmpStringList.length >= 12 ? this.conf.setAttr('ifUseModel', Boolean.parseBoolean(tmpStringList[11])) : this.conf.setAttr('ifUseModel', false)
      tmpStringList.length >= 13 ? this.conf.setAttr('ifUseGitManagerModel', Boolean.parseBoolean(tmpStringList[12])) : this.conf.setAttr('ifUseGitManagerModel', false)
      tmpStringList.length >= 14 ? this.conf.setAttr('ifSaveModelBuildComputer', Boolean.parseBoolean(tmpStringList[13])) : this.conf.setAttr('ifSaveModelBuildComputer', false)
      tmpStringList.length >= 15 ? this.conf.setAttr('modelGitRepository', tmpStringList[14]) : this.conf.setAttr('modelGitRepository', '')
      tmpStringList.length >= 16 ? this.conf.setAttr('deployEnvStatus', tmpStringList[15]) : this.conf.setAttr('deployEnvStatus', 'start')
      tmpStringList.length >= 17 ? this.conf.setAttr('deployEnv', tmpStringList[16]) : this.conf.setAttr('deployEnv', 'dev')
      tmpStringList.length >= 17 ? this.conf.setAttr('nodeEnv', tmpStringList[16]) : this.conf.setAttr('nodeEnv', 'dev')
      tmpStringList.length >= 18 ? this.conf.setAttr('ifUseIstio', Boolean.parseBoolean(tmpStringList[17])) : this.conf.setAttr('ifUseIstio', false)
      tmpStringList.length >= 19 ? this.conf.setAttr('ifUseApolloOfflineEnv', Boolean.parseBoolean(tmpStringList[18])) : this.conf.setAttr('ifUseApolloOfflineEnv', false)
      tmpStringList.length >= 20 ? this.conf.setAttr('yamlEnv', tmpStringList[19]) : this.conf.setAttr('yamlEnv', 'None')
      tmpStringList.length >= 21 ? this.conf.setAttr('androidFlavor', tmpStringList[20]) : this.conf.setAttr('androidFlavor', 'default')
    }

    def apolloEnvList = ['dev', 'test', 'prd', 'stage', 'uat']

    // APOLLO_ENV
    this.conf.setAttr('apolloEnv', this.conf.getAttr('deployEnv'))

    if (!(this.conf.getAttr('deployEnv') in apolloEnvList)) {
      this.conf.setAttr('apolloEnv', 'prd')
      this.conf.setAttr('nodeEnv', 'prd')
    }

    if (this.conf.getAttr('deployEnv') == 'mlcloud-dev') {
      this.conf.setAttr('apolloEnv', 'dev')
    }

    // APOLLO_CONFIG_ADDRESS
    this.conf.setAttr('apolloConfigAddress', 'http://' + this.conf.getAttr('apolloEnv') + '-conf.apollo.cc.dm-ai.cn')


    if (this.conf.getAttr('deployEnv') != 'prd' && this.conf.getAttr('buildPlatform') == 'adp') {
      this.conf.setAttr('domain', this.conf.getAttr('jobName') + '.' + this.conf.getAttr('namespace') + '.' + this.conf.getAttr('deployEnv') + '.dm-ai.cn')
    }

    // domain https
    if (this.conf.getAttr('https')) {
      if (!(this.conf.getAttr('deployEnv') in ['prd', 'dev', 'test', 'stage'])) {
        this.conf.setAttr('domain', this.conf.getAttr('jobName') + '-' + this.conf.getAttr('namespace') + '-' + this.conf.getAttr('deployEnv') + '.dm-ai.cn')
      }
      if (this.conf.getAttr('deployEnv') in ['dev', 'test', 'stage']) {
        this.conf.setAttr('domain', this.conf.getAttr('jobName') + '-' + this.conf.getAttr('namespace') + '.' + this.conf.getAttr('deployEnv') + '.dm-ai.cn')
      }
    }
    
    // if (this.conf.getAttr('deployEnv') in this.conf.externalK8sEnv) { //如果是外部环境，不创建域名，因为外部的ingres规则和dmai的traefik不兼容
    if (this.conf.getAttr('deployEnvStatus') == 'stop' && this.conf.getAttr('deployEnv') != 'chuanyin') { //如果是外部离线环境，不创建域名，因为外部的ingres规则和dmai的traefik不兼容
            this.conf.setAttr('domain', '')
    }

  }

  public String getJenkinsRunTemplate(String deployMasterPassword, String deployEnvironment, params) {
    this.deployMasterPassword = deployMasterPassword
    this.conf.setAttr('deployEnv', deployEnvironment)
    this.setConfInitPara(params)

    def returnString = this.templateTop() +
                this.templateDockerCompile() +
                this.templateADP() +
                this.templateJiagu() +
                this.templateSonarCheck() +
                this.customImage() +
                this.defaultVolumes()
    return returnString
  }
  public String getJenkinsRunTemplateOnJar() {
    // println( this.templateTop())
    // println( this.templateDockerCompile() )
    return this.templateTop() + this.templateDockerCompile()
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
  - ip: 192.168.3.13
    hostnames:
    - nexus.dm-ai.cn
  - ip: 192.168.11.4
    hostnames:
    - adp.dm-ai.cn
  imagePullSecrets:
  - name: regsecret
  containers:
'''
  }


  private String useModelPath() {
    if (this.conf.getAttr('useModel') && this.conf.getAttr('modelPath')) {
      return String.format('''
    - name: jenkins-build-path
      mountPath: /models
      subPath: models/%s/%s/%s
''', this.conf.getAttr('namespace'), this.conf.getAttr('deployEnv'), this.conf.appName)
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

  private String templateJsCompilevolumeMounts() {
    //        if (this.conf.getAttr('makeImage') && this.conf.getAttr('codeLanguage') in ['js', 'node']) {
    if (this.conf.getAttr('codeLanguage') in ['js', 'node', 'nodets']) {
      return String.format('''
    volumeMounts:
    - name: jenkins-build-path
      mountPath: /data/cache/node_modules
      subPath: jenkins_home/node_cache/%s/%s
''', this.conf.appName, this.conf.getAttr('branchName'))
    }
    return ''
}
  private String templateADP() {
    return String.format('''
  - name: adp
    imagePullPolicy: IfNotPresent
    image: docker.dm-ai.cn/devops/base-image-adp:0.5.17%s
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
''', this.conf.getAttr('envType') == 'arm' ? '-arm' : '', this.conf.vueAppScene, this.useModelPath())
  }

  private String templateJiagu() {
    if (this.conf.getAttr('codeLanguage') == 'unity' || this.conf.getAttr('codeLanguage') == 'android') {
      return String.format('''
  - name: jiagu
    image: docker.dm-ai.cn/devops/android-jiagu:0.1.3
    imagePullPolicy: IfNotPresent
    env:
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    - name: ADP_DATE
      value: %s
    volumeMounts:
    - name: jenkins-build-path
      mountPath: /data
      subPath: android_home/unity_home/%s/%s
    command:
    - "sleep"
    args:
    - "3600"
    tty: true
''', this.adpDate, this.conf.appName, this.conf.getAttr('deployEnv'))
    }
    return ''
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
    if (this.conf.appName == 'aia-webrtc-stream') {
      return String.format('''
  - name: compile
    image: docker.dm-ai.cn/public/centos-node:v1.0
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
    }

    if (this.conf.getAttr('codeLanguage') == 'node' && this.conf.getAttr('envType') == 'arm') {
      return String.format('''
  - name: compile
    image: %s
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
''', this.conf.getAttr('ifCompileImage') ? this.conf.getAttr('compileImage') : 'docker.dm-ai.cn/arm64/node:10.16.3-slim-tx2', this.templateJsCompilevolumeMounts())
    }

    //        if (! this.conf.getAttr('compile')) return ''
    switch (this.conf.getAttr('codeLanguage')) {
            case 'js':
        return String.format('''
  - name: compile
    image: %s
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
''', this.conf.getAttr('ifCompileImage') ? this.conf.getAttr('compileImage') : 'docker.dm-ai.cn/devops/base-image-compile-frontend:0.03', this.templateJsCompilevolumeMounts())

            case 'android': return String.format('''
  - name: compile
    image: docker.dm-ai.cn/devops/dm-android:0.8.14
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    volumeMounts:
    - name: jenkins-build-path
      mountPath: /data
      subPath: android_home/%s/%s
    - name: jenkins-build-path
      mountPath: /android_cache
      subPath: android_cache/%s/%s
    - name: jenkins-build-path
      mountPath: /unity_data
      subPath: android_home/unity_home/%s/%s
    command:
    - "sleep"
    args:
    - "3600"
    tty: true
''', this.conf.appName, this.conf.getAttr('deployEnv'), this.conf.appName, this.conf.getAttr('deployEnv'), this.conf.getAttr('unity_app_name'), this.conf.getAttr('deployEnv'))

            case 'unity': return String.format('''
  - name: compile
    image: docker.dm-ai.cn/devops/base-image-unity:0.1.1
    imagePullPolicy: IfNotPresent
    env: #指定容器中的环境变量
    - name: DMAI_PRIVATE_DOCKER_REGISTRY
      value: docker.dm-ai.cn
    - name: ADP_DATE
      value: %s
    volumeMounts:
    - name: jenkins-build-path
      mountPath: /data
      subPath: android_home/unity_home/%s/%s
    - name: jenkins-build-path
      mountPath: /root/.cache/unity3d
      subPath: unity_cache/%s/%s
    - name: jenkins-build-path
      mountPath: /root/.local/share/unity3d/Unity
      subPath: unity_share/%s/%s
    command:
    - "sleep"
    args:
    - "3600"
    tty: true
''', this.adpDate, this.conf.appName, this.conf.getAttr('deployEnv'), this.conf.appName, this.conf.getAttr('deployEnv'), this.conf.appName, this.conf.getAttr('deployEnv'))

            case 'node':
        return String.format('''
  - name: compile
    image: %s
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
''', this.conf.getAttr('ifCompileImage') ? this.conf.getAttr('compileImage') : 'docker.dm-ai.cn/devops/node:0.0.4', this.templateJsCompilevolumeMounts())

            case 'nodets':
        return String.format('''
  - name: compile
    image: %s
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
''', this.conf.getAttr('ifCompileImage') ? this.conf.getAttr('compileImage') : 'docker.dm-ai.cn/devops/node:0.0.4', this.templateJsCompilevolumeMounts())

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
    image: docker.dm-ai.cn/devops/base-image-mvn:0.1.2
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
    - "3600"
    tty: true
''', this.conf.appName)
            case 'jar':
        return '''
  - name: compile
    image: docker.dm-ai.cn/devops/base-image-mvn:0.1.2
    imagePullPolicy: IfNotPresent
    command:
    - "sleep"
    args:
    - "3600"
    tty: true
'''
            case 'golang':
        return String.format('''
  - name: compile
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
''', this.conf.getAttr('ifCompileImage') ? this.conf.getAttr('compileImage') : 'docker.dm-ai.cn/devops/base-image-golang-compile:master-2-1e85e1e99bfee20f6f0cc5de5a74ce339100d4bd')
            default:
                return ''
    }
  }

  }
