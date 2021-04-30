package com.dmai


// 制作docker镜像

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

    // 使用默认的Dockerfile
    public void changeDockerfileToDefault(){
        this.createDockerignore()
        this.script.sh String.format('echo %s > Dockerfile', this.dockerFileTemplate.getDockerFile())
        this.pullEnvToDockerfileFromApollo()
    }

    // 使用adp应用管理里面配置的Dockerfile
    public void changeDockerfileToAdpConfig(){
        this.createDockerignore()
        // this.script.sh String.format('echo %s > Dockerfile', this.conf.getAttr('customDockerfileContent'))
        this.script.sh "echo '${this.conf.getAttr('customDockerfileContent')}' > Dockerfile"
        this.pullEnvToDockerfileFromApollo()
    }

    // 使用代码根目录下面的Dockerfile
    public void changeDockerfileToGitRootDir(){
        this.createDockerignore()
        this.pullEnvToDockerfileFromApollo()
    }

    public void pullEnvToDockerfileFromApollo() {
        //从apollo拉取配置注入Dockerfile
        if (this.conf.getAttr('ifUseApolloForDockerfile')) {
            this.script.sh "echo -e '\n' >> Dockerfile"
            try {
                this.script.sh String.format('/usr/bin/tools-get-apollo-data-write-dockerfile --config_server_url=http://%s-conf.apollo.cc.dm-ai.cn  --appId=%s  --clusterName="%s"  --namespaceName="%s"  --Dockerfile=`pwd`/Dockerfile',
                        this.conf.getAttr('apolloEnvForDockerfile'),
                        this.conf.getAttr('jobName'),
                        this.conf.getAttr('apolloClusterForDockerfile'),
                        this.conf.getAttr('apolloNamespaceForDockerfile'))
            } catch (e) {
                this.conf.setAttr('deployRes', '构建离线部署环境的镜像，从apollo拉取数据失败，请检查apollo配置或者网络问题')
                this.conf.setAttr('deployMsg', '构建离线部署环境的镜像，从apollo拉取数据失败，请检查apollo配置或者网络问题')
                throw e
            }
        }

        if (this.conf.getAttr('deployEnvStatus') == 'start' ) {
            this.script.sh "echo -e '\nENV deployEnvStatus=online' >> Dockerfile"
        }else{
            this.script.sh "echo 'deployEnvStatus=offline' >> Dockerfile"
        }
        this.script.sh 'cat Dockerfile'
    }

    public void makeImage() {
        this.script.sh 'pwd;tree -L 2'
        this.script.sh String.format('docker-compose build --build-arg VUE_APP_SCENE=%s --build-arg MODEL_VERSION=%s --build-arg FRONTEND_ENV=%s  service-docker-build',
            this.conf.vueAppScene, 
            this.conf.modelVersion, 
            this.conf.getAttr('nodeEnv'))
    }

    private void createDockerignore() {
        this.script.sh 'touch .dockerignore'
        this.script.sh 'echo .git > .dockerignore; echo Dockerfile >> .dockerignore;echo Jenkinsfile >> .dockerignore; echo docker-compose.yml >> .dockerignore; echo deployment >> .dockerignore'
    }

    public void pushImage() {
        this.script.sh 'docker-compose push'
    }

}
