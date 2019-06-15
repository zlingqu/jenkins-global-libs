package com.dmai

import com.tool.Tools

class JenkinsRunTemplate {
    private Conf conf

    JenkinsRunTemplate(Conf conf) {
        this.conf = conf
    }

    public String getJenkinsRunTemplate() {
        return this.templateTop() + this.templateDockerCompile() + this.templateDockerKubectl() + this.templateDockerCompose()
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

    private String templateDockerKubectl() {
        if (this.conf.getAttr('deploy')) {
            return  String.format('''
  - name: kubectl 
    image: docker.dm-ai.cn/devops/base-image-kubectl:%s-0.01
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
''', this.conf.getAttr('branchName') == 'master' ? this.conf.getAttr('branchName'): this.conf.getAttr('dev'))
        } else {
            return ''
        }
    }

    private String templateDockerCompile() {
        switch (this.conf.getAttr('codeLanguage')) {
            case 'js':
                return '''
  - name: compile
    image: docker.dm-ai.cn/devops/base-image-compile-frontend:0.03
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
        memory: 5000Mi
        cpu: 3000m
      requests:
        cpu: 2000m
        memory: 4000Mi
'''
            default:
                return ''
        }
    }

    private String templateDockerJenkins() {
        return '''
  - name: jnlp 
    image: docker.dm-ai.cn/devops/base-image-jenkins-jnlp-slave:0.01
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
}

