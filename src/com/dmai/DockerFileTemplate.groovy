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
        }
    }

    private String getNodeDockerfile() {
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
