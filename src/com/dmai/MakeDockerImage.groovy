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
        if (this.conf.getAttr('deployEnvStatus') == 'stop' && this.conf.getAttr('ifUseApolloOfflineEnv')) {
            this.script.sh "echo -e '\n' >> Dockerfile"
            try {
                this.script.sh String.format('/usr/bin/tools-get-apollo-data-write-dockerfile --config_server_url=%s --appId=%s --clusterName="%s" --namespaceName="%s" --Dockerfile=`pwd`/Dockerfile',
                        this.conf.getAttr('apolloConfigAddress'),
                        this.conf.getAttr('jobName'),
                        this.conf.getAttr('apolloClusterName'),
                        this.conf.getAttr('apolloNamespace'))
                this.script.sh "echo 'deployEnvStatus=offline' >> Dockerfile"
                this.script.sh 'cat Dockerfile'
            } catch (e) {
                this.conf.setAttr('deployRes', '构建离线部署环境的镜像，从apollo拉取数据失败，请检查apollo配置或者网络问题')
                this.conf.setAttr('deployMsg', '构建离线部署环境的镜像，从apollo拉取数据失败，请检查apollo配置或者网络问题')
                throw e
            }
        }

        if (this.conf.getAttr('deployEnvStatus') == 'start' ) {
            this.script.sh "echo -e '\nENV deployEnvStatus=online' >> Dockerfile"
            this.script.sh 'cat Dockerfile'
        }

        // ### 需要处理 1。 使用环境变量的。 2. 有些业务是没配置文件的。注意。

        this.script.sh "echo '${this.dockerFileTemplate.getDockerComposeFile()}' > docker-compose.yml"

        // 在进行构建之前复制需要的模型文件
        if (this.conf.getAttr('useModel') && this.conf.getAttr('modelPath') && !this.conf.getAttr('ifUseGitManagerModel')) {
            this.script.sh "mkdir -p ${this.conf.getAttr('modelPath')}; cp -rp /models/* ${this.conf.getAttr('modelPath')}"
        }


        this.script.sh String.format('pwd;ls;docker-compose build --build-arg VUE_APP_SCENE=%s --build-arg MODEL_VERSION=%s --build-arg FRONTEND_ENV=%s service-docker-build',
                this.conf.vueAppScene, this.conf.modelVersion, this.conf.getAttr('nodeEnv')
        )

    }

    private void createDockerignore() {
        this.script.sh 'touch .dockerignore'
        //        this.script.sh "echo .git > .dockerignore; echo Dockerfile >> .dockerignore;echo Jenkinsfile >> .dockerignore; echo deployment >> .dockerignore; echo docker-compose.yml >> .dockerignore"
        this.script.sh 'echo .git > .dockerignore; echo Dockerfile >> .dockerignore;echo Jenkinsfile >> .dockerignore; echo docker-compose.yml >> .dockerignore; echo deployment >> .dockerignore'
    }

    public void pushImage() {
        this.script.sh 'docker-compose push'
    }

}
