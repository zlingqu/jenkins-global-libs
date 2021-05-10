package com.dmai


// kaniko处理docker image

class Kaniko {

    protected final def script
    private final Conf conf

    Kaniko(script, Conf conf) {
        this.script = script
        this.conf = conf
    }
    public void makeAndPushImage() {
        // this.script.sh 'echo quzl'
        this.script.sh echo ' echo "{\"auths\":{\"$DOCKER_REGIS_URL\":{\"username\":\"$DOCKER_LIB_USER\",\"password\":\"$DOCKER_LIB_PASSWORD\"}}}" > /kaniko/.docker/config.json'
        this.script.sh String.format('pwd && tree -L 2 && /kaniko/executor --verbosity error --context ${WORKSPACE} --dockerfile ${WORKSPACE}/Dockerfile --build-arg MODEL_VERSION=%s --build-arg FRONTEND_ENV=%s --build-arg VUE_APP_SCENE=%s --destination %s',
        this.conf.modelVersion,
        this.conf.getAttr('nodeEnv'),
        this.conf.vueAppScene,
        this.conf.getAttr('buildImageAddress'))


        // this.script.sh String.format('cp -r * /workspace && cd /workspace && pwd && tree -L 2 && /kaniko/executor --context /workspace --dockerfile /workspace/Dockerfile --build-arg MODEL_VERSION=%s --build-arg FRONTEND_ENV=%s --build-arg VUE_APP_SCENE=%s --destination %s',
        // this.conf.modelVersion,
        // this.conf.getAttr('nodeEnv'),
        // this.conf.vueAppScene,
        // this.conf.getAttr('buildImageAddress'))


        // this.script.sh "pwd && tree -L 2 && executor --context " +
        // "${WORKSPACE}" +
        // "--build-arg MODEL_VERSION=${this.conf.modelVersion}" +
        // "--build-arg FRONTEND_ENV=${this.conf.modelVersion}" +
        // "--build-arg VUE_APP_SCENE=${this.conf.getAttr('nodeEnv')}" +
        // "--destination ${this.conf.getAttr('buildImageAddress')}"
    }
}