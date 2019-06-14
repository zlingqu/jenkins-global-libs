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
        }
    }

    private String getJsDockerfile() {
        return '''
FROM docker.dm-ai.cn/devops/base-image-compile-run-frontend:0.01
ENV TZ=Asia/Shanghai
ADD dist /usr/share/nginx/html
ADD nginx.conf /etc/nginx/conf.d/default.conf
RUN ln -sf /dev/stdout /var/log/nginx/access.log
RUN ln -sf /dev/stderr /var/log/nginx/error.log
EXPOSE 80
ENTRYPOINT nginx -g "daemon off;"
'''
    }

    private String getNodeDockerfile() {
        if (this.conf.appName == 'storage-service')
            return '''
FROM docker.dm-ai.cn/public/node:10.16-alpine
MAINTAINER 秦小波
ENV TZ=Asia/Shanghai
ADD . /usr/local/storage-service
WORKDIR /usr/local/storage-service
RUN  npm config set registry http://192.168.3.13:8081/repository/npm && npm install -g node-gyp && npm install
#对外暴露的端口
EXPOSE 3000
#程序启动脚本
CMD ["npm", "start"]
'''
        return '''
FROM docker.dm-ai.cn/devops/node-10:0.0.1
WORKDIR /app
COPY package*.json ./
RUN npm config set registry http://192.168.3.13:8081/repository/npm/ && npm install
COPY . .
VOLUME ["/app/data"]
CMD [ "npm", "start" ]
'''
    }

    public String getDockerComposeFile() {
        def text = '''
version: "2"
services:
  service-docker-build:
    build: ./
    image: $dockerRegistryHost/$namespace/$appName:$branchName-$buildNumber
'''

        def binding = [
                'dockerRegistryHost' : conf.dockerRegistryHost,
                'namespace' : conf.getAttr('namespace'),
                'appName' : conf.appName,
                'branchName' : conf.getAttr('branchName'),
                'buildNumber' : conf.getAttr('buildNumber')
        ]

        return Tools.simpleTemplate(text, binding)
    }
}
