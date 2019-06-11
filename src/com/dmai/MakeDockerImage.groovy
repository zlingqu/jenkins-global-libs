package com.dmai

class MakeDockerImage {

    protected final def script
    private final Conf conf
    private DockerFileTemplate dockerFileTemplate

    MakeDockerImage(script, Conf conf) {
        this.script = script
        this.conf = conf
    }

    public void makeImage() {
        this.dockerFileTemplate = new DockerFileTemplate(this.conf)

        if (! conf.getAttr('customDockerfile')) {
            this.script.sh "echo '${this.dockerFileTemplate.getDockerFile()}' > Dockerfile"
        }

        this.script.sh "echo '${ this.dockerFileTemplate.getDockerComposeFile() }' > docker-compose.yml"

        this.script.sh 'docker-compose build'
    }
}
