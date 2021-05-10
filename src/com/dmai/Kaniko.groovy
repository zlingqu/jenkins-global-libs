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
        this.script.sh 'echo quzl'
        this.script.sh String.format('pwd && tree -L 2 && executor --ignore-path /busybox --ignore-path /kaniko -v info -c ${WORKSPACE} -f ${WORKSPACE}/Dockerfile --build-arg MODEL_VERSION=%s --build-arg FRONTEND_ENV=%s --build-arg VUE_APP_SCENE=%s --destination %s',
        this.conf.modelVersion,
        this.conf.getAttr('nodeEnv'),
        this.conf.vueAppScene,
        this.conf.getAttr('buildImageAddress'))
    }
}