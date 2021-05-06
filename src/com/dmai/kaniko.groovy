package com.dmai


// 制作docker镜像

class Kaniko {

    protected final def script
    private final Conf conf
    private DockerFileTemplate dockerFileTemplate

    Kaniko(script, Conf conf) {
        this.script = script
        this.conf = conf
        this.dockerFileTemplate = new DockerFileTemplate(this.conf)
    }
    public void makeAndPushImage() {
        this.script.sh '/kaniko/executor --destination $IMAGE_TAG_NAME' + this.conf.getAttr('buildImageAddress')
    }
}
