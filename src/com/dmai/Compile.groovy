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
                            " cp -rp /data/cache/node_modules ./;" +
                            "npm config set registry=http://192.168.3.13:8081/repository/npm/ && npm install && npm run build" +
                            "; cp -rp node_modules /data/cache/node_modules"
                    return
                case 'c++':
                    this.script.sh "make"
                    return
            }
        }
    }
}
