package com.dmai
import com.tool.Tools

//docker-compose处理docker image

class MakeDockerImage {

    protected final def script
    private final Conf conf

    MakeDockerImage(script, Conf conf) {
        this.script = script
        this.conf = conf
    }

    public String getDockerComposeFile() {
        def text = '''
version: "2"
services:
  service-docker-build:
    build: ./
    image: $imageAddress'''

        def binding = [
                'imageAddress': this.conf.getAttr('buildImageAddress'),
        ]

        return Tools.simpleTemplate(text, binding)
    }


    public void makeDockerComposeYml() {
        this.script.sh "echo '${getDockerComposeFile()}' > docker-compose.yml"

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
