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
        this.script.sh 'pwd && tree -L 2 && executor --context ${WORKSPACE} --destination ' + this.conf.getAttr('buildImageAddress')
    }
}