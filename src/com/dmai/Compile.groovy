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
                    this.script.sh "test -d node_modules && rm -fr node_modules;" +
                            "sleep 6000;"
                            "npm config set cache '/data/cache/node_modules/node_cache';" +
                            "npm config set prefix '/data/cache/node_modules/node_cache/node_global';" +
                            "export NODE_PATH='/data/cache/node_modules/node_modules';" +
                            "npm config set registry=http://192.168.3.13:8081/repository/npm/ && npm install && npm run build"
                    return
                case 'c++':
                    this.script.sh "make"
                    return
            }
        }
    }
}
