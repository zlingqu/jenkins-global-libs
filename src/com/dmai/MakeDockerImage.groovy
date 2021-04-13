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

    public void makeDockerComposeYml() {
        this.script.sh "echo '${this.dockerFileTemplate.getDockerComposeFile()}' > docker-compose.yml"

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

        //从apollo拉取配置注入Dockerfile
        if (this.conf.getAttr('ifUseApolloForDockerfile')) {
            this.script.sh "echo -e '\n' >> Dockerfile"
            try {
                this.script.sh String.format('/usr/bin/tools-get-apollo-data-write-dockerfile --config_server_url=http://%s-conf.apollo.cc.dm-ai.cn --appId=%s --clusterName="%s" --namespaceName="%s" --Dockerfile=`pwd`/Dockerfile',
                        this.conf.getAttr('apolloEnvForDockerfile'),
                        this.conf.getAttr('jobName'),
                        this.conf.getAttr('apolloClusterForDockerfile'),
                        this.conf.getAttr('apolloNamespaceForDockerfile'))
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

        // this.script.sh "echo '${this.dockerFileTemplate.getDockerComposeFile()}' > docker-compose.yml"

        this.script.sh String.format('pwd;ls -lR /models ;docker-compose build --build-arg VUE_APP_SCENE=%s --build-arg MODEL_VERSION=%s --build-arg FRONTEND_ENV=%s service-docker-build',
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
