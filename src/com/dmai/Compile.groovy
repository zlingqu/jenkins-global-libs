package com.dmai

class Compile {

    protected final def script
    private final Conf conf

    Compile( script, Conf conf ){
        this.script = script
        this.conf = conf
    }

    public void compile(){
        if (this.conf.getAttr('compile')) {
            switch (this.conf.getAttr('codeLanguage')) {
                case 'js':
                    this.script.sh "npm config set registry=http://192.168.3.13:8081/repository/npm/ && npm install && npm run build"
            }
        }
    }
}
