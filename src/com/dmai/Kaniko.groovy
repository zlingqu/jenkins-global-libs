package com.dmai


// 制作docker镜像

class Kaniko {

    protected final def script
    private final Conf conf

    Kaniko(script, Conf conf) {
        this.script = script
        this.conf = conf
    }
    public void makeAndPushImage() {
        this.script.sh 'echo quzl'
        this.script.sh 'executor --destination $IMAGE_TAG_NAME' + this.conf.getAttr('buildImageAddress')
//         this.script.sh """\
// #!/busybox/sh -e
// echo quzl
// """
    }
}