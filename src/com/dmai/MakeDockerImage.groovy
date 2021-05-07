package com.dmai


//docker-compose处理docker image

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
        this.script.sh 'pwd;tree -L 2'
        this.script.sh String.format('docker-compose build --build-arg VUE_APP_SCENE=%s --build-arg MODEL_VERSION=%s --build-arg FRONTEND_ENV=%s  service-docker-build',
            this.conf.vueAppScene,
            this.conf.modelVersion,
            this.conf.getAttr('nodeEnv'))
    }

    public void pushImage() {
        this.script.sh 'docker-compose push'
    }

}
