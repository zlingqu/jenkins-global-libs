package com.dmai

class MakeDockerImage {

    protected final def script

    MakeDockerImage(script) {
        this.script = script
    }

    public void makeImage() {
        this.script.echo "first test"
    }
}
