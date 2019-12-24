package com.dmai
import com.tool.Tools

class DockerFileTemplate {

    private Conf conf

    DockerFileTemplate(Conf conf) {
        this.conf = conf
    }

    public String getDockerFile() {
        // 如果用户自定义dockerfile，则输出空字符串
        if (conf.getAttr('customDockerfile')) {
            return ''
        }

        switch (conf.getAttr('codeLanguage')) {
            case 'node':
                return this.getNodeDockerfile()
            case 'js':
                return this.getJsDockerfile()
            case 'c++':
                return this.getCppDockerfile()
            case 'nginx':
                return  this.getNginxDockerfile()
            case 'java':
                return  this.getJavaDockerfiel()
            case 'golang':
                return this.getGolangDockerfile()
            case 'nodets':
                return this.getNodeTsDockerfile()
        }
    }

    private String getGolangDockerfile() {
        return String.format('''
FROM docker.dm-ai.cn/devops/base-image-golang-run-env:tag-v0.0.3
WORKDIR /workspace
ADD ./build ./build
ADD ./Makefile /workspace/Makefile
EXPOSE 80
CMD ["make", "run"]
''', this.conf.getAttr('jobName'), this.conf.getAttr('jobName'), this.conf.getAttr('jobName'))
    }

    private String getCppDockerfile() {
        return '''
FROM docker.dm-ai.cn/devops/media-access:r.13
ENV TZ=Asia/Shanghai
ADD . /src
CMD '/src/run.sh'
'''
    }

    private  String getJavaDockerfiel() {
        return '''
FROM docker.dm-ai.cn/devops/base-image-java8:0.0.1
ENV TZ=Asia/Shanghai
WORKDIR /app
ADD ./deploy /app
EXPOSE 3000
ENTRYPOINT ["bash", "./start.sh"]
'''
    }

    private String getJsDockerfile() {
        if (this.conf.getAttr('envType') == 'arm') {
            return '''
FROM docker.dm-ai.cn/arm64/nginx:1.17.4-alpine-tx2
ENV TZ=Asia/Shanghai
ADD dist /usr/share/nginx/html
ADD nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ENTRYPOINT nginx -g "daemon off;"
'''
        }
        return '''
FROM docker.dm-ai.cn/devops/base-image-compile-run-frontend:0.02
ENV TZ=Asia/Shanghai
ADD dist /usr/share/nginx/html
ADD nginx.conf /etc/nginx/conf.d/default.conf
RUN ln -sf /dev/stdout /var/log/nginx/access.log
RUN ln -sf /dev/stderr /var/log/nginx/error.log
EXPOSE 80
ENTRYPOINT nginx -g "daemon off;"
'''
    }

    private String getNginxDockerfile() {
        return '''
FROM docker.dm-ai.cn/devops/base-image-compile-run-frontend:0.02
ENV TZ=Asia/Shanghai
ADD nginx.conf /etc/nginx/nginx.conf
RUN ln -sf /dev/stdout /var/log/nginx/access.log
RUN ln -sf /dev/stderr /var/log/nginx/error.log
EXPOSE 80
ENTRYPOINT nginx -g "daemon off;"
'''
    }

    private String getNodeTsDockerfile() {
        return '''
FROM docker.dm-ai.cn/devops/node:0.0.4
WORKDIR /app
COPY . .
RUN npm config set registry http://192.168.3.13:8081/repository/npm && npm install && tsc
ENV TZ="Asia/Shanghai"
ENTRYPOINT [ "npm","start" ]
'''
    }

    private String getNodeDockerfile() {
        if (this.conf.getAttr('envType') == 'arm') {
            return '''
FROM docker.dm-ai.cn/arm64/node:10.16.3-slim-tx2
WORKDIR /app
COPY . .
RUN npm config set registry http://192.168.3.13:8081/repository/npm && npm install
ENV TZ="Asia/Shanghai"
ENTRYPOINT [ "npm","start" ]
'''
        }

        return '''
FROM docker.dm-ai.cn/devops/node:0.0.4
WORKDIR /app
COPY . .
RUN npm config set registry http://192.168.3.13:8081/repository/npm && npm install
ENV TZ="Asia/Shanghai"
ENTRYPOINT [ "npm","start" ]
'''
    }

    public String getDockerComposeFile() {
        def text = '''
version: "2"
services:
  service-docker-build:
    build: ./
    image: $imageAddress'''

        def binding = [
                'imageAddress': this.conf.getAttr('buildImageAddress'),
        ]

        return Tools.simpleTemplate(text, binding)
    }
}
