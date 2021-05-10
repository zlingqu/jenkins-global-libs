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
        // this.script.sh echo ' echo "{\"auths\":{\"$DOCKER_REGIS_URL\":{\"username\":\"$DOCKER_LIB_USER\",\"password\":\"$DOCKER_LIB_PASSWORD\"}}}" > /kaniko/.docker/config.json'
        // this.script.sh String.format('pwd && tree -L 2 && /kaniko/executor --verbosity error --context ${WORKSPACE} --dockerfile ${WORKSPACE}/Dockerfile --build-arg MODEL_VERSION=%s --build-arg FRONTEND_ENV=%s --build-arg VUE_APP_SCENE=%s --destination %s',
        // this.conf.modelVersion,
        // this.conf.getAttr('nodeEnv'),
        // this.conf.vueAppScene,
        // this.conf.getAttr('buildImageAddress'))

        // this.script.sh echo ' echo "{\"auths\":{\"$DOCKER_REGIS_URL\":{\"username\":\"$DOCKER_LIB_USER\",\"password\":\"$DOCKER_LIB_PASSWORD\"}}}" > /kaniko/.docker/config.json'
        sh '''
        #!/busybox/sh
        pwd && ls
        '''

    }
}