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

        if (! conf.getAttr('customDockerfile')) {
            this.script.sh "echo '${this.dockerFileTemplate.getDockerFile()}' > Dockerfile"
        }

        this.script.sh "echo '${ this.dockerFileTemplate.getDockerComposeFile() }' > docker-compose.yml"

//        if (this.conf.appName == 'xmc2-frontend') {
            this.script.sh String.format('docker-compose build --build-arg VUE_APP_SCENE=%s --build-arg VUE_APP_SCHOOL=%s service-docker-build',
                    this.conf.vueAppScene, this.conf.vueAppSchool
            )
//            return
//        }
//        this.script.sh 'docker-compose build'
    }

    private void createDockerignore() {
        this.script.sh "touch .dockerignore"
        this.script.sh "echo .git > .dockerignore; echo Dockerfile >> .dockerignore;echo Jenkinsfile >> .dockerignore; echo deployment >> .dockerignore; echo docker-compose.yml >> .dockerignore"
    }

    public void pushImage() {
        this.script.sh 'docker-compose push'

    }
}
