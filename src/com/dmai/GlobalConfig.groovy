package com.dmai

class GlobalConfig implements Serializable {
    public Map<String, Map<String, String>> globalConfig = [
            'frontend-test' : [
                    'nodePort': '31377'
            ],
            'work-attendance-frontend': [
                    'servicePort' : '80',
                    'namespace': 'mis',
//                    'nodePort': '30800',
                    'containerPort': '80',
                    'domain': 'watt.mis.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/admin/work-attendance-frontend.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'policy-information-point': [
                    'servicePort' : '80',
                    'namespace': 'cum',
                    'nodePort' : '30801',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '800Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/cum/cum-admin/policy-information-point.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'policy-retrieval-point': [
                    'servicePort' : '80',
                    'namespace': 'cum',
                    'nodePort' : '30802',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '800Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/cum/cum-admin/policy-retrieval-point.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'sync-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30223',
                    'containerPort': '3000',
                    'domain': '',// domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'replicas' : 2,
                    'cpuLimits' : '2000m',
                    'memoryLimits' : '4000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/sync-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
           'xmc2-data-sync': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '',
                    'containerPort': '3000',
                    'domain': '',// domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'replicas' : 1,
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/sync-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
             'xmc2-proxy-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30227',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prddd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/ui-backend-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : true, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : '', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : false, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'ui-backend-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30227',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/ui-backend-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'engine-video-extract': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30222',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '4000m',
//                    'memoryRequests' : '2000Mi',
//                    'cpuLimits' : '24000m',
//                    'memoryLimits' : '8000Mi',
                    'replicas' : 4,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-video-extract-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //挂载的configmap的名称
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'golang', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'all', //分为gpu 和非gpu的环境, 不做限制
                    'sonarCheck'  : true
            ],
            'media-gateway': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30224',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '2000m',
                    'memoryLimits' : '4000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/media-gateway.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'anyCf': true, //多个configmap
                    'configMapName': 'config.js', //挂载的configmap的名称
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'c++', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'command': 'command: ["/bin/sh", "-c", "cp -rp /src/debug/* /tmp && cd /tmp && ./MediaGateway"]', //自定义命令行
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true,
            ],
            'monitor-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30237',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
                    'replicas' : 2,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/monitor-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //挂载的configmap的名称
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'media-access': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30225',
                    'containerPort': '8080',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/media-access.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'anyCf': true, //多个configmap
                    'configMapName': 'config.js', //挂载的configmap的名称
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'c++', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'command': 'command: ["/bin/sh", "-c", "cp -rp /src/debug/* /tmp && cd /tmp && ./MediaAccess"]', //自定义命令行
                    'tcpPort': [31600, 31800],
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'dispatcher-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30226',
                    'containerPort': '3000',
                    'domain': 'dispatcher.chongwen.xmc2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas': 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/dispatcher-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'showcase': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30997',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/showcase.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'storage-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '1000m',
                    'memoryRequests' : '2000Mi',
                    'replicas' : 3,
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '4000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/storage-service.git',
                    'stage': true, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': true,  //是否需要挂载存储
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'storage-benchmark': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30243',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/wujiaxi/xmc2-storage-benchmark.git',
                    'stage': true, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': false,  //是否需要挂载存储
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'stat-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30221',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '2000m',
                    'memoryLimits' : '4000Mi',
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/stat-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'vod-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30230',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/vod-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'meta-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30231',
                    'containerPort': '3000',
                    'domain': 'meta.chongwen.xmc2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/meta-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'meta-adapter': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30232',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/meta-adapter.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'engine-audio-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30228',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '2000m',
//                    'memoryRequests' : '2000Mi',
//                    'cpuLimits' : '12000m',
//                    'memoryLimits' : '16000Mi',
//                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-voice-cls-service.git',
                    'useModel': true,
                    'modelPath': 'app/data', //模型文件path, 在构建的时候相对于当前的代码的主目录
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
            ],
            'engine-image-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30235',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-cv-service.git',
                    'useModel': true,
                    'modelPath': 'app/data', //模型文件path, 在构建的时候相对于当前的代码的主目录
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
            ],
            'script-auto-gen': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '30236',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
//                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/tool/script-auto-gen.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'script-auto-gen-frontend': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
//                    'nodePort' : '30236',
                    'containerPort': '80',
                    'domain': 'script-auto-gen.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/tool/script-auto-gen-frontend.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'ta-classroom-frontend': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '30238',
                    'containerPort': '80',
                    'domain': 'classroom-x2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-classroom-frontend.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'rdac-frontend': [
                    'servicePort' : '80',
                    'namespace': 'rdac',
                    'containerPort': '80',
                    'domain': 'rdac.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/RDAC/rdac-frontend.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'rdac-api-gateway': [
                    'servicePort' : '80',
                    'namespace': 'rdac',
//                    'nodePort' : '30240',
                    'containerPort': '80',
                    'domain': 'api-rdac.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/RDAC/rdac-api-gateway.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
//                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
            ],
            'rdac-backend': [
                    'servicePort' : '80',
                    'namespace': 'rdac',
                    'nodePort' : '30239',
                    'containerPort': '3000',
//                    'domain': 'rdac.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/RDAC/rdac-backend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'rdac-device-collector': [
                    'servicePort' : '80',
                    'namespace': 'rdac',
                    'nodePort' : '30241',
                    'containerPort': '3000',
//                    'domain': 'rdac.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/RDAC/rdac-device-collector.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
//            'rdac-ota-service': [
//                    'servicePort' : '80',
//                    'namespace': 'rdac',
//                    'nodePort' : '30240',
//                    'containerPort': '3000',
////                    'domain': 'rdac.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuLimits' : '200m',
//                    'memoryLimits' : '400Mi',
//                    'replicas' : 1,
//                    'dev': 'dev', // dev分支部署到测试环境
//                    'test': true, // 是否从dev分支部署到测试环境
//                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
//                    'gitAddress': 'https://gitlab.dm-ai.cn/RDAC/rdac-ota-service.git',
//                    'compile': false, // 是否编译
//                    'deploy': true, // 是否自动化部署
//                    'customDockerfile': true, // 是否使用自定义 dockerfile
//                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
//                    'useConfigMap': true, //是否使用configmap
//                    'configMapName': '.env', //是否使用configmap
//                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
//                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
//                    'k8sKind': 'deployment', // 部署的服务的类型
//                    'useStore': true, // 是否使用存储资源。
//                    'storePath' : '/data',
//                    'useService': true, // 是否使用service
//                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
//                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
//            ],
            'rdac-license-manager': [
                    'servicePort' : '80',
                    'namespace': 'rdac',
                    'nodePort' : '30242',
                    'containerPort': '3000',
//                    'domain': 'rdac.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/RDAC/rdac-license-manager.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
            ],
            'engine-pipeline-manager': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30234',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-pipline-mgr-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的configmap.yaml的内容来给容器注入环境变量。
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
            ],
            'engine-metric-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort' : '30229',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
//                    'cpuLimits' : '2000m',
//                    'memoryLimits' : '4000Mi',
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-metric-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
            ],
            'xmc2-frontend': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-chongwen',
                    'nodePort': '30233',
                    'containerPort': '80',
                    'domain': 'chongwen.xmc2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '800Mi',
                    'replicas' : 2,
                    'dev-domain' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-frontend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'buildArg' : true, //使用自定义dockerfile的时候，是否注入环境变量，VUE_APP_SCENE=xxxx，支持前端一个项目通过参数构建出不同的项目
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'mis-admin-frontend': [
                    'servicePort' : '80',
                    'namespace': 'mis',
                    'nodePort': '30092',
                    'containerPort': '80',
                    'domain': 'hr.mis.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/admin/mis-admin-frontend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'project-tracking-frontend': [
                    'servicePort' : '80',
                    'namespace': 'mis',
                    'nodePort': '30096',
                    'containerPort': '80',
//                    'domain': 'hr.mis.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/admin/mis-admin-frontend.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'app-deploy-platform-axure': [
                    'servicePort' : '80',
                    'namespace': 'devops',
                    'nodePort': '30199',
                    'containerPort': '80',
                    'domain': 'app-deploy-platform-axure', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/app-deploy-platform-axure.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'mis-org-frontend': [
                    'servicePort' : '80',
                    'nodePort': '30093',
                    'namespace': 'mis',
                    'containerPort': '80',
                    'domain': 'org.mis.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/org/org-frontend.git',
                    'compile': true, //
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'mis-org-backend': [
                    'servicePort' : '80',
                    'namespace': 'mis',
                    'nodePort' : '31501',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/org/org-backend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
			'xmc-mock-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort': '',
                    'containerPort': '80',
                    'domain': 'xmc.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'dev-domain' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prdd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-mock-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'xmc-frontend': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort': '',
                    'containerPort': '80',
                    'domain': 'xmc.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'dev-domain' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-frontend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
	    'xmc-metric-generator': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'replicas' : 3,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-metric-generator.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
		'xmc-backend-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31147',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'replicas' : 3,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-backend-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
		'xmc-data-stream': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-data-stream.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
		'xmc-data-collector': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31141',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'replicas' : 3,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-data-collector.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
		'xmc-storage-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'replicas' : 3,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-storage-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tx2-engine-audio-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-tx2',
                    'nodePort' : '30228',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tx2/engine-audio-process.git',
                    'useModel': true,
                    'modelPath': 'app/data', //模型文件path, 在构建的时候相对于当前的代码的主目录
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
            ],
            'tx2-engine-image-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-tx2',
                    'nodePort' : '30235',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。,
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-cv-service.git',
                    'useModel': true,
                    'modelPath': 'app/data', //模型文件path, 在构建的时候相对于当前的代码的主目录
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : false
            ],
            'tx2-engine-video-extract': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-tx2',
                    'nodePort' : '30222',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'replicas' : 4,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-video-extract-service.git',
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //挂载的configmap的名称
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'golang', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'all', //分为gpu 和非gpu的环境, 不做限制
                    'sonarCheck'  : false
            ],
            'tx2-engine-metric-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-tx2',
                    'nodePort' : '30229',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-metric-service.git',
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : false
            ],
             'tx2-engine-pipeline-manager': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-tx2',
                    'nodePort' : '30234',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-pipline-mgr-service.git',
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的configmap.yaml的内容来给容器注入环境变量。
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : false
            ],
		'tx2-stat-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-tx2',
                    'envType'     : 'arm',
                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'replicas' : 3,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tx2/stat-service.git',
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tx2-task-manager': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-tx2',
                    'envType'     : 'arm',
                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'replicas' : 3,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tx2/task-manager.git',
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tx2-web-server-backend': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-tx2',

                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'replicas' : 3,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tx2/web-server-backend.git',
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tx2-web-server': [
                    'servicePort' : '80',
                    'namespace': 'xmc2-tx2',
                    'envType'     : 'arm',
                    'nodePort': '',
                    'containerPort': '80',
                    'domain': 'xmc.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'dev-domain' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tx2/web-server.git',
                    'compile': true, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-frontend': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort': '',
                    'containerPort': '80',
                    'domain': 'xmc-tk.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'dev-domain' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-frontend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': '', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-storage-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '200m',
                    'memoryRequests' : '2000Mi',
                    'replicas' : 3,
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2/storage-service.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': true,  //是否需要挂载存储
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-offline-task': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-offline-task.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-task-manager': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-task-manager.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-backend-system-overview': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-backend-system-overview.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': false,  //是否需要挂载存储
                    'useStore': false, // 是否使用存储资源
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-backend-lesson-list': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-backend-lesson-list.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': false,  //是否需要挂载存储
                    'useStore': false, // 是否使用存储资源
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-backend-user-validation': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-backend-user-validation.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': false,  //是否需要挂载存储
                    'useStore': false, // 是否使用存储资源
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-backend-portrait': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-backend-portrait.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': false,  //是否需要挂载存储
                    'useStore': false, // 是否使用存储资源
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-backend-teaching-report': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-backend-teaching-report.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': false,  //是否需要挂载存储
                    'useStore': false, // 是否使用存储资源
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-backend-learning-report': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-backend-learning-report.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': false,  //是否需要挂载存储
                    'useStore': false, // 是否使用存储资源
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-backend-daily': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-backend-daily.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': false,  //是否需要挂载存储
                    'useStore': false, // 是否使用存储资源
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-backend-lesson-detail': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-backend-lesson-detail.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': false,  //是否需要挂载存储
                    'useStore': false, // 是否使用存储资源
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-backend-teaching-quality': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '50m',
                    'memoryRequests' : '50Mi',
                    'replicas' : 1,
                    'cpuLimits' : '200m',
                    'memoryLimits' : '200Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-backend-teaching-quality.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-metrics': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '',
                    'containerPort': '',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址
                    'cpuRequests' : '200m',
                    'memoryRequests' : '200Mi',
                    'replicas' : 1,
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/metrics.git',
                    'stage': false, //是否部署到stage环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'storage': false,  //是否需要挂载存储
                    'useStore': false, // 是否使用存储资源。
                    'storePath' : '/data',
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'tk-engine-video-extract': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'replicas' : 2,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-engine-video-extract.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'golang', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'all', //分为gpu 和非gpu的环境, 不做限制
                    'sonarCheck'  : true
            ],
             'tk-engine-audio-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '',
                    'containerPort': '', // 没有端口监听，不需要svc
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '1000m',
                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-engine-audio-process.git',
                    'useModel': false,
                    'modelPath': '', //模型文件path, 在构建的时候相对于当前的代码的主目录
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/data',
                    'useService': false, // 是否使用service
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'cpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
            ],
            'tk-engine-image-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
                    'nodePort' : '',
                    'containerPort': '',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-engine-image-process.git',
                    'useModel': true,
                    'modelGitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-models.git', //请按此格式书写，http，不要git@
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'command': 'command: ["python", "main.py", "--config", "config-student.yaml"]', //自定义pod启动的命令行
//                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'tk-engine-image-process-student': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
//                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-engine-image-process.git',
                    'useModel': true,
                    'modelGitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-models.git', //请按此格式书写，http，不要git@
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'command': 'command: ["python", "main.py", "--config", "config-student.yaml"]', //自定义pod启动的命令行
//                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'tk-engine-image-process-teacher': [
                    'servicePort' : '80',
                    'namespace': 'xmc-tk',
//                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-engine-image-process.git',
                    'useModel': true,
                    'modelGitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-tk/xmc-models.git', //请按此格式书写，http，不要git@
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'command': 'command: ["python", "main.py", "--config", "config-teacher.yaml"]', //自定义pod启动的命令行
//                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'xmc-offline-task': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
//                    'nodePort' : '31171',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-offline-task.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'xmc-xc-model-serving': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
//                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-xc-model-serving.git',
                    'useModel': true,
                    'modelGitAddress': 'http://192.168.3.29:8082/eng-team-models/XMC/xmc_models.git', //请按此格式书写，http，不要git@
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'command': 'command: ["python", "main.py", "--config", "config-student.yaml"]', //自定义pod启动的命令行
//                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'xmc-model-serving-student': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
//                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-xc-model-serving.git',
                    'useModel': true,
                    'modelGitAddress': 'http://192.168.3.29:8082/eng-team-models/XMC/xmc_models.git', //请按此格式书写，http，不要git@
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'command': 'command: ["python", "main.py", "--config", "config-student.yaml"]', //自定义pod启动的命令行
//                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'xmc-model-serving-teacher': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
//                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc-xc-model-serving.git',
                    'useModel': true,
                    'modelGitAddress': 'http://192.168.3.29:8082/eng-team-models/XMC/xmc_models.git', //请按此格式书写，http，不要git@
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'command': 'command: ["python", "main.py", "--config", "config-teacher.yaml"]', //自定义pod启动的命令行
//                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'xmc-online-api': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31166',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'test', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/zhengwenyong/xmc_online_api.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
//                    'storePath' : '/app/data',
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'facial-expression-cls': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31170',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '1000Mi',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'test', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'http://192.168.3.29:8082/research-team-models/CV/image-classification-face-expression/face-expression-F0002M01S0002.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
//                    'storePath' : '/app/data',
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'xmc-detection-api': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31167',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '1000Mi',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'test', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/research-algorithm/CV-lib/Products/XMC/detection.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
//                    'storePath' : '/app/data',
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'xmc-gesture-api': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31168',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '1000Mi',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'test', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/research-algorithm/CV-lib/Products/XMC/gesture.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
//                    'storePath' : '/app/data',
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'xmc-holdobj-api': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31169',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '1000Mi',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'test', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/research-algorithm/CV-lib/Products/XMC/holdobj.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.js', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
//                    'storePath' : '/app/data',
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'mis-admin-backend': [
                    'servicePort' : '80',
                    'nodePort': '31500',
                    'namespace': 'mis',
                    'containerPort': '5000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/admin/mis-admin-backend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'service-prometheus' : [
                    'nodePort': '30090',
                    'namespace': 'devops',
                    'containerPort': '9090',
                    'domain': 'http://prometheus.ops.dm-ai.cn',
                    'kubectlImage': 'devops/base-image-kubectl:0.01',
            ],
            'prometheus-pushgateway': [
                    'servicePort' : '80',
                    'namespace': 'devops',
                    'nodePort' : '30991',
                    'containerPort': '9091',
                    'domain': 'prom.dm-ai.cn',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/pushgateway.git',
                    'kubectlImage': 'devops/base-image-kubectl:0.01',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'golang', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'service-push-exporter-data': [
                    'servicePort' : '80',
                    'namespace': 'devops',
//                    'nodePort' : '30991',
                    'containerPort': '8080',
//                    'domain': 'prom.dm-ai.cn',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/service-push-exporter-data.git',
                    'compile': true, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'golang', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'service-k8s-app-status-check': [
                    'servicePort' : '80',
                    'namespace': 'devops',
//                    'nodePort' : '30991',
                    'containerPort': '8080',
                    'domain': 'service-k8s-app-status-check.dm-ai.cn',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/service-k8s-app-status-check.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'golang', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'service-k8s-nodes-status-check': [
                    'servicePort' : '80',
                    'namespace': 'devops',
//                    'nodePort' : '30991',
                    'containerPort': '8080',
//                    'domain': 'service-k8s-app-status-check.dm-ai.cn',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/service-k8s-nodes-status-check.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'golang', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'service-operate-jenkins': [
                    'servicePort' : '80',
                    'namespace': 'devops',
//                    'nodePort' : '30991',
                    'containerPort': '8080',
                    'domain': 'service-operate-jenkins.dm-ai.cn',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/service-operate-jenkins.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'golang', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'service-k8s-status-check': [
                    'servicePort' : '80',
                    'namespace': 'devops',
//                    'nodePort' : '30991',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'containerPort': '80',
//                    'domain': 'prom.dm-ai.cn',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/service-k8s-status-check.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'golang', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'xmc2-test-deploy': [
                    'servicePort' : '80',
                    'nodePort': '31500',
                    'namespace': 'mis',
                    'containerPort': '5000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'test', // dev分支部署到开发环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/admin/mis-admin-backend.git',
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : false, // 是否进行镜像的构造，打镜像，push镜像
//
                    'useCustomImage': true, //使用非标准自定义的镜像, 在jenkins的运行环境中
                    'customImage' : 'docker.dm-ai.cn/devops/xmc2-test-deploy:0.05', // 使用自定义镜像的地址
                    'execCommand' : 'python main.py',
                    'sonarCheck'  : true

            ],
            'jenkins-model-ci-test': [
                    'servicePort' : '80',
                    'nodePort': '31500',
                    'namespace': 'mis',
                    'containerPort': '5000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'test', // dev分支部署到开发环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/admin/mis-admin-backend.git',
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : false, // 是否进行镜像的构造，打镜像，push镜像
//
                    'useCustomImage': true, //使用非标准自定义的镜像, 在jenkins的运行环境中
                    'customImage' : 'docker.dm-ai.cn/algorithm-research/torch:0.4_cuda9_cudnn7_py27', // 使用自定义镜像的地址
                    'execCommand' : 'cd test; bash -x run_test.sh',
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
            ],
            'polyaxon-chart': [
                    'servicePort' : '80',
                    'nodePort': '31500',
                    'namespace': 'mis',
                    'containerPort': '5000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'test', // dev分支部署到开发环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/admin/mis-admin-backend.git',
                    'compile': false, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : false, // 是否进行镜像的构造，打镜像，push镜像
//
                    'useCustomImage': true, //使用非标准自定义的镜像, 在jenkins的运行环境中
                    'customImage' : 'docker.dm-ai.cn/devops/base-image-helm-client:dev-0.01', // 使用自定义镜像的地址
                    'execCommand' : 'helm install -f config.yaml --name polyaxon --namespace polyaxon ./polyaxon || helm upgrade polyaxon ./polyaxon -f config.yaml || sleep 6000',
                    'envType'     : 'cpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
            ],
            'ta-server' : [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31221',
                    'containerPort': '8888',
                    'domain': 'prepare-server-x2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-server.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'ta-wechat-service': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'containerPort': '8888',
                    'domain': 'wechat-service-x2.deploy-env.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '800Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-wechat-service.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
            ],
            'ta-launcher-management': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'containerPort': '8888',
                    'domain': 'launcher-management-x2.deploy-env.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-launcher-management.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
            ],
            'x3-web': [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'containerPort': '80',
                    'domain': 'frontend-x3.deploy-env.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '300m',
                    'memoryLimits' : '500Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/X3-Web.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
            ],
            'ta-scheduler': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31230',
                    'containerPort': '8888',
//                    'domain': 'prepare-server-x2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-scheduler.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'ta-live-server': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31229',
                    'containerPort': '3000',
//                    'domain': 'prepare-server-x2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '300m',
                    'memoryLimits' : '600Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-live-server.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'ta-face-recognition-service': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31228',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
//                    'cpuLimits' : '1000m',
//                    'memoryLimits' : '2000Mi',
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-face-recognition-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'useModel': true,
                    'modelPath': 'app/data', //模型文件path, 在构建的时候相对于当前的代码的主目录
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
//                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
//                    'useStore': true, // 是否使用存储资源。
//                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : false
            ],
            'resource-service': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31224',
                    'containerPort': '9999',
                    'domain': 'resources-x2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/resource-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'content-producer': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31225',
                    'containerPort': '8888',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/ecosystem/content-producer.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false,
            ],
            'cp2-webgl-simulator': [
                    'servicePort' : '80',
                    'namespace': 'cp',
//                    'nodePort' : '31225',
                    'containerPort': '80',
                    'domain': 'cp2-simulator.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/CP/simulator.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false,
            ],
            'ta-admin-server': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31222',
                    'containerPort': '8888',
                    'domain': 'admin-server-x2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-admin-server.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'ta-stats-server': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31227',
                    'containerPort': '3000',
                    'domain': 'stats-server-x2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-stats-server.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'ta-classroom-server': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31223',
                    'containerPort': '8888',
                    'domain': 'classroom-server-x2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-classroom-server.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'auto-deployment-service': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31226',
                    'containerPort': '80',
                    'domain': 'auto-deployment-service-x2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/tool/auto-deployment-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/app/data',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],

            'project-tracking-backend': [
                    'servicePort' : '80',
                    'namespace': 'mis',
                    'nodePort' : '30094',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '800Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/org/project-tracking/project-tracking-backend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'x3-frontend': [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort': '',
                    'containerPort': '80',
                    'domain': 'frontend-x3.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '',
                    'memoryLimits' : '',
                    'dev-domain' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/X3students.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'x3-dm-server' : [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30334',
                    'containerPort': '5200',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x3/x3-dm-server.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'x3-content-manager-user-service': [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30337',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/x3-content-manager-user-service.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'x3-content-manager-label-service': [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30338',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/x3-content-manager-label-service.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'x3-content-manager-static-resource-service': [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30339',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/x3-content-manager-static-resource-service.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/data',
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'x3-content-manager-structure-service': [
                    'servicePort' : '80',
                    'namespace': 'x3',
//                    'nodePort' : '30339',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/x3-content-manager-structure-service.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'x3-proxy-service': [
                    'servicePort' : '80',
                    'namespace': 'x3',
//                    'nodePort' : '30339',
                    'containerPort': '80',
                    'domain': 'x3-proxy-service.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/proxy-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'nginx', // 临时的，默认是【js,node,golang,java,php,python,nginx]
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'x2-ta-proxy-service':[
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
//                    'nodePort' : '30339',
                    'containerPort': '80',
                    'domain': 'x2-ta-proxy-service.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/x2-ta-proxy-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'nginx', // 临时的，默认是【js,node,golang,java,php,python,nginx]
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'x3-content-manager-question-service': [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30341',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/x3-content-manager-question-service.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': '', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'x3-tts-serving' : [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30330',
                    'containerPort': '3000',
                    'domain': 'tts-serving.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x3/tts-serving.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.ini', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': true, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'x3-content-manager-front': [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort': '30233',
                    'containerPort': '80',
                    'domain': 'content-manager-front-x3.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '800Mi',
                    'replicas' : 1,
                    'dev-domain' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/X3-Content-Manager-Front.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'buildArg' : true, //使用自定义dockerfile的时候，是否注入环境变量，VUE_APP_SCENE=xxxx，支持前端一个项目通过参数构建出不同的项目
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'x3-teacher-android': [
                    'servicePort' : '80',
                    'namespace': 'x3',
//                    'nodePort': '30233',
                    'containerPort': '80',
//                    'domain': 'content-manger-front-x3.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '800Mi',
                    'replicas' : 1,
                    'dev-domain' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/X3_teacher_android.git',
                    'compile': true, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'buildArg' : false, //使用自定义dockerfile的时候，是否注入环境变量，VUE_APP_SCENE=xxxx，支持前端一个项目通过参数构建出不同的项目
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'android', // 临时的，默认是【js,node,golang,java,php,python, android】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : false, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'x3-content-manager-service' : [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30335',
                    'containerPort': '3000',
                    'domain': 'x3-cm.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/X3-Content-Manager-Service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'useStore': true, // 是否使用存储资源。
                    'storePath' : '/data',
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'x3-core-algorithm' : [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30333',
                    'containerPort': '3000',
                    'domain': 'core-algorithm.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x3/Core-Algorithm.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'x3-ocr-model-serving-mathpix' : [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30331',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x3/ocr-model-serving-mathpix.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'x3-dispatcher-service': [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30332',
                    'containerPort': '3000',
                    'domain': 'x3.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/X3-dispatcher-service.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'x3-teacher-backend': [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30336',
                    'containerPort': '3000',
                    'domain': 'backend-x3.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/X3-teacher-backend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'ta-prepare-client': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '30127',
                    'containerPort': '80',
                    'domain': 'x2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-prepare-frontend.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'x2-essayanalyzer': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '30410',
                    'containerPort': '5000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '1500m',
                    'memoryRequests' : '6500Mi',
                    'cpuLimits' : '2000m',
                    'memoryLimits' : '8000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/research-algorithm/nlp-lib/EssayAnalyzer.git',
//                    'useModel': true,
//                    'modelPath': 'models', //模型文件path, 在构建的时候相对于当前的代码的主目录
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.ini', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
//                    'useStore': true, // 是否使用存储资源。
//                    'storePath' : '/app/models',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'ta-speech-eval-gateway': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-speech-eval-gateway.git',
//                    'useModel': true,
//                    'modelPath': 'models', //模型文件path, 在构建的时候相对于当前的代码的主目录
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //���否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
//                    'useStore': true, // 是否使用存储资源。
//                    'storePath' : '/app/models',
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'ta-essay-ocr-service': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-essay-ocr-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'x2-language-tool': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '30411',
                    'containerPort': '8081',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
//                    'cpuLimits' : '1000m',
//                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/research-algorithm/nlp-lib/LanguageTool.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.ini', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false
            ],
            'blackbox-exporter': [
                    'namespace': 'devops',
                    'containerPort': '9115',
                    'domain': '9115',
                    'nodePort': '30915',
                    'kubectlImage': 'devops/base-image-kubectl:dev-0.01'
            ],
            'app-deploy-platform-frontend': [
                    'servicePort' : '80',
                    'namespace': 'devops',
//                    'nodePort': '30092',
                    'containerPort': '80',
                    'domain': 'app-deploy-platform.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/app-deploy-platform/app-deploy-platform-frontend.git',
                    'compile': true, // 是否编译
                    'customCompileCommand' : 'npm config set registry http://192.168.3.13:8081/repository/npm/ &&  npm install && npm run build',
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'dm-design': [
                    'servicePort' : '80',
                    'namespace': 'devops',
//                    'nodePort': '30092',
                    'containerPort': '80',
                    'domain': 'design.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/weihaopeng/dm-design.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'cp2-aog': [
                    'servicePort' : '80',
                    'namespace': 'cp',
//                    'nodePort': '30092',
                    'containerPort': '80',
                    'domain': 'cp2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/wangqing/aog-gui.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'cp2-device-connector': [
                    'servicePort' : '80',
                    'namespace': 'cp',
                    'nodePort': '30095',
                    'containerPort': '8000',
                    'domain': 'cp2-connector.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/CP/pilot/cp-device-connector.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'app-deploy-platform-env-manger': [
                    'servicePort' : '80',
                    'namespace': 'devops',
                    'nodePort': '30100',
                    'containerPort': '5000',
//                    'domain': 'app-deploy-platform.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '300m',
                    'memoryLimits' : '600Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/app-deploy-platform/app-deploy-platform-env-manger.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'app-deploy-platform-space-manger': [
                    'servicePort' : '80',
                    'namespace': 'devops',
                    'nodePort': '30101',
                    'containerPort': '5000',
//                    'domain': 'app-deploy-platform.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '300m',
                    'memoryLimits' : '600Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/app-deploy-platform/app-deploy-platform-space-manger.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'app-deploy-platform-deploy-manger': [
                    'servicePort' : '80',
                    'namespace': 'devops',
                    'nodePort': '30102',
                    'containerPort': '5000',
                    'domain': 'app-deploy-platform-deploy-manger.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/app-deploy-platform/app-deploy-platform-deploy-manger.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'app-deploy-platform-project-manger': [
                    'servicePort' : '80',
                    'namespace': 'devops',
                    'nodePort': '30103',
                    'containerPort': '5000',
//                    'domain': 'app-deploy-platform.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '200m',
                    'memoryLimits' : '400Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/app-deploy-platform/app-deploy-platform-project-manger.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'work-attendance': [
                    'servicePort' : '80',
                    'namespace': 'mis',
                    'nodePort': '30101',
                    'containerPort': '5000',
//                    'domain': 'app-deploy-platform.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '300m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/admin/work-attendance.git',
                    'compile': true, // 是否编译
                    'deploy': false, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'java', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : false, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'ta-advanced-stats-server': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort': '30244',
                    'containerPort': '8080',
//                    'domain': 'app-deploy-platform.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
//                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-advanced-stats-server.git',
                    'compile': true, // 是否编译
                    'customCompileCommand': 'mvn -Dmaven.test.skip=true package',
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'java', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'x2-musi-api': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort': '30245',
                    'containerPort': '8001',
//                    'domain': 'app-deploy-platform.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '1000m',
                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1500m',
                    'memoryLimits' : '3000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/research-algorithm/nlp-lib/MusiAI.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'java', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'xmart-court-frontend': [
                    'servicePort' : '80',
                    'namespace': 'xmart-court',
                    'nodePort': '30340',
                    'containerPort': '80',
                    'domain': 'xmart-court.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'domain': 'app-deploy-platform.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '300m',
                    'memoryLimits' : '600Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/PX/court-group/xmart-court-frontend.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'xmart-court-backend': [
                    'servicePort' : '80',
                    'namespace': 'xmart-court',
                    'nodePort' : '31223',
                    'containerPort': '3000',
                    'domain': 'xmart-court-backend.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
//                    'cpuLimits' : '500m',
//                  'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/PX/court-group/xmart-court-backend',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
//                    'configMapName': '.env', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, //  是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'jenkins-test': [
                    'servicePort' : '80',
                    'namespace': 'devops',
                    'nodePort' : '30444',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-prepare-frontend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : true, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'useStore': false, // 是否使用存储资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ]
    ]
}
