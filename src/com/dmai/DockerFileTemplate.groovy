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
        }
    }

    private String getCppDockerfile() {
        return '''
FROM docker.dm-ai.cn/devops/media-access:r.12
ADD . /src
CMD '/src/run.sh'
'''
    }

    private String getJsDockerfile() {
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

    private String getNodeDockerfile() {
        return '''
FROM docker.dm-ai.cn/devops/node:0.0.2
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
    image: $dockerRegistryHost/$namespace/$appName:$branchName-$buildNumber
'''

        def binding = [
                'dockerRegistryHost' : conf.dockerRegistryHost,
                'namespace' : conf.getAttr('namespace'),
                'appName' : conf.appName,
                'branchName' : conf.getAttr('branchName'),
                'buildNumber' : conf.getAttr('buildNumber'),
        ]

        return Tools.simpleTemplate(text, binding)
    }
}
