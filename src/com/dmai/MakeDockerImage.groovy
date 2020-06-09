package com.dmai

class MakeDockerImage {

    protected final def script
    private final Conf conf
    private DockerFileTemplate dockerFileTemplate

    MakeDockerImage(script, Conf conf) {
        this.script = script
        this.conf = conf
        this.dockerFileTemplate = new DockerFileTemplate(this.conf)
    }

    public void makeImage() {
        //创建docker构建的时候的排除文件。
        this.createDockerignore()

        if (!conf.getAttr('customDockerfile')) {
            this.script.sh "echo '${this.dockerFileTemplate.getDockerFile()}' > Dockerfile"
        }

        if (conf.getAttr('customDockerfile') && !this.conf.getAttr('ifUseRootDockerfile') && this.conf.getAttr('buildPlatform') == 'adp') {
            this.script.sh "echo '${this.conf.getAttr('customDockerfileContent')}' > Dockerfile"
        }

        //如果不是部署在公司的k8s环境中的镜像，在倒数第二行灌入配置文件。
        if (this.conf.getAttr('deployEnv') in this.conf.privateK8sEnv) {
            try {
                def tmpConfigFilePath = String.format("deployment/%s/%s/%s", this.conf.getAttr('namespace'), this.conf.getAttr('deployEnv'), this.conf.getAttr('jobName'))
                this.script.sh String.format('echo "ADD %s %s" >> Dockerfile; echo "RUN rm -fr deployment || pwd" >> Dockerfile', tmpConfigFilePath, this.conf.getAttr('configFilePath'))
            } catch (e) {
                this.script.sh "echo 在deployment下未找到配置文件，开始在apollo中查找数据，并写入环境变量到dockerdile中！"
                this.script.sh String.format('/usr/bin/tools-get-apollo-data-write-dockerfile --config_server_url=%s --appId=%s --clusterName="%s" --namespaceName="%s" --Dockerfile=`pwd`/Dockerfile',
                        this.conf.getAttr('apolloConfigAddress'),
                        this.conf.getAttr('jobName'),
                        this.conf.getAttr('apolloClusterName'),
                        this.conf.getAttr('apolloNamespace'))
            }
        }

        if (this.conf.getAttr('deployEnvStatus') == 'stop') {
            try {
                this.script.sh String.format('/usr/bin/tools-get-apollo-data-write-dockerfile --config_server_url=%s --appId=%s --clusterName="%s" --namespaceName="%s" --Dockerfile=`pwd`/Dockerfile',
                        this.conf.getAttr('apolloConfigAddress'),
                        this.conf.getAttr('jobName'),
                        this.conf.getAttr('apolloClusterName'),
                        this.conf.getAttr('apolloNamespace'))
            } catch (e) {
                this.conf.setAttr('deployRes', '构建离线部署环境的镜像，从apollo拉取数据失败，请检查apollo配置或者网络问题')
                this.conf.setAttr('deployMsg', '构建离线部署环境的镜像，从apollo拉取数据失败，请检查apollo配置或者网络问题')
                throw e
            }
        }
        // ### 需要处理 1。 使用环境变量的。 2. 有些业务是没配置文件的。注意。


        this.script.sh "echo '${this.dockerFileTemplate.getDockerComposeFile()}' > docker-compose.yml"

        // 在进行构建之前复制需要的模型文件
        if (this.conf.getAttr('useModel') && this.conf.getAttr('modelPath') && !this.conf.getAttr('ifUseGitManagerModel')) {
            this.script.sh "mkdir -p ${this.conf.getAttr('modelPath')}; cp -rp /models/* ${this.conf.getAttr('modelPath')}"
        }

//        // teshu chuli
//        if (this.conf.getAttr('buildPlatform') == 'adp' && this.conf.getAttr('compile') && this.conf.getAttr('compileParam') && this.conf.getAttr('codeLanguage') == 'js') {
//            this.script.sh String.format('pwd;ls;docker-compose build %s service-docker-build', this.conf.getAttr('compileParam')
//            )
//            return
//        }

        // 对 xmc2-frontend做特殊处理。
//        if (this.conf.appName == 'xmc2-frontend') {
        this.script.sh String.format('pwd;ls;docker-compose build --build-arg VUE_APP_SCENE=%s --build-arg MODEL_VERSION=%s --build-arg FRONTEND_ENV=%s service-docker-build',
                this.conf.vueAppScene, this.conf.modelVersion, this.conf.getAttr('nodeEnv')
        )
//            return
//        }

//        this.script.sh 'docker-compose build'
    }

    private void createDockerignore() {
        this.script.sh "touch .dockerignore"
//        this.script.sh "echo .git > .dockerignore; echo Dockerfile >> .dockerignore;echo Jenkinsfile >> .dockerignore; echo deployment >> .dockerignore; echo docker-compose.yml >> .dockerignore"
        this.script.sh "echo .git > .dockerignore; echo Dockerfile >> .dockerignore;echo Jenkinsfile >> .dockerignore; echo docker-compose.yml >> .dockerignore; echo deployment >> .dockerignore"
    }

    public void pushImage() {
        this.script.sh 'docker-compose push'

    }
}
