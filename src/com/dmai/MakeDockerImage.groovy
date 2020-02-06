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

        if (conf.getAttr('customDockerfile') && this.conf.getAttr('customDockerfileContent'))
        {
            this.script.sh "echo '${this.conf.getAttr('customDockerfileContent')}' > Dockerfile"
        }

        this.script.sh "echo '${ this.dockerFileTemplate.getDockerComposeFile() }' > docker-compose.yml"

        // 在进行构建之前复制需要的模型文件
        if (this.conf.getAttr('useModel') && this.conf.getAttr('modelPath')) {
            this.script.sh "mkdir -p ${this.conf.getAttr('modelPath')}; cp -rp /models/* ${this.conf.getAttr('modelPath')}"
        }
        // teshu chuli
        if (this.conf.getAttr('buildPlatform') == 'adp' && this.conf.getAttr('compile') && this.conf.getAttr('compileParam') && this.conf.getAttr('codeLanguage') == 'js') {
            this.script.sh String.format('pwd;ls;docker-compose build %s service-docker-build', this.conf.getAttr('compileParam')
            )
            return
        }

        // 对 xmc2-frontend做特殊处理。
//        if (this.conf.appName == 'xmc2-frontend') {
            this.script.sh String.format('pwd;ls;docker-compose build --build-arg VUE_APP_SCENE=%s --build-arg VUE_APP_SCHOOL=%s --build-arg MODEL_VERSION=%s --build-arg FRONTEND_ENV=%s service-docker-build',
                    this.conf.vueAppScene, this.conf.vueAppSchool, this.conf.modelVersion, this.conf.getAttr('nodeEnv')
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
