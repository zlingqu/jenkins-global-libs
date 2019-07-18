package com.dmai

class GlobalConfig implements Serializable {
    public Map<String, Map<String, String>> globalConfig = [
            'frontend-test' : [
                    'nodePort': '31377'
            ],
            'work-attendance-frontend': [
                    'servicePort' : '80',
                    'namespace': 'mis',
                    'nodePort': '30800',
                    'containerPort': '80',
                    'domain': 'wattdev.mis.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
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
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'sync-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30223',
                    'containerPort': '3000',
                    'domain': '',// domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'ui-backend-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30227',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'engine-video-extract': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30222',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '6000m',
                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '8000m',
                    'memoryLimits' : '4000Mi',
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'media-gateway': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30224',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'media-access': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30225',
                    'containerPort': '8080',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
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
                    'udpPort': [31600, 31699],
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'dispatcher-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30226',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas': 3,
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'showcase': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'storage-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '4000Mi',
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像

            ],
            'stat-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30221',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '2000m',
                    'memoryLimits' : '4000Mi',
                    'replicas' : 1,
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'vod-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30230',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'meta-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30231',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'meta-adapter': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30232',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'engine-audio-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30228',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '2000m',
                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '4000m',
                    'memoryLimits' : '4000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-voice-cls-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'engine-image-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30235',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-cv-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'envType'     : 'gpu' //分为gpu 和非gpu的环境
            ],
            'engine-pipeline-manager': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30234',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
//                    'cpuLimits' : '800m',
//                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-xc/xmc2-pipline-mgr-service.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'engine-metric-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30229',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
//                    'cpuLimits' : '2000m',
//                    'memoryLimits' : '4000Mi',
                    'replicas' : 1,
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'xmc2-frontend': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort': '30233',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '200m',
//                    'memoryRequests' : '400Mi',
//                    'cpuLimits' : '400m',
//                    'memoryLimits' : '800Mi',
                    'dev-domain' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/XMC/xmc2-frontend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prdd', // 如果参数master 不等于prd，整个构建就失败，---
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
			'xmc-backend-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31147',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prdd', // 如果参数master 不等于prd，整个构建就失败，---
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
			'xmc-data-stream': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prdd', // 如果参数master 不等于prd，整个构建就失败，---
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prdd', // 如果参数master 不等于prd，整个构建就失败，---
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
			'xmc-storage-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc',
                    'nodePort' : '31165',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '',
                    'memoryRequests' : '',
                    'cpuLimits' : '2000m',
                    'memoryLimits' : '4000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prdd', // 如果参数master 不等于prd，整个构建就失败，---
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'service-prometheus' : [
                    'nodePort': '30090',
                    'namespace': 'devops',
                    'containerPort': '9090',
                    'domain': 'http://prometheus.ops.dm-ai.cn',
                    'kubectlImage': 'devops/base-image-kubectl:0.01',
            ],
            'prometheus-alertmanager': [
                    'servicePort' : '9093',
                    'namespace': 'devops',
                    'nodePort' : '30993',
                    'containerPort': '9093',
                    'domain': 'alertmanager.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '500m',
//                    'memoryRequests' : '1000Mi',
//                    'cpuLimits' : '1000m',
//                    'memoryLimits' : '1500Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/application-engineering/devops/service-prometheus.git',
                    'kubectlImage': 'devops/base-image-kubectl:0.01',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'prometheus-alertmanager', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : false, // 是否进行镜像的构造，打镜像，push镜像
//
                    'useCustomImage': true, //使用非标准自定义的镜像, 在jenkins的运行环境中
                    'customImage' : 'docker.dm-ai.cn/devops/xmc2-test-deploy:0.05', // 使用自定义镜像的地址
                    'execCommand' : 'python main.py',

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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : false, // 是否进行镜像的构造，打镜像，push镜像
//
                    'useCustomImage': true, //使用非标准自定义的镜像, 在jenkins的运行环境中
                    'customImage' : 'docker.dm-ai.cn/algorithm-research/torch:0.4_cuda9_cudnn7_py27', // 使用自定义镜像的地址
                    'execCommand' : 'cd test; bash -x run_test.sh',
                    'envType'     : 'gpu' //分为gpu 和非gpu的环境
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : false, // 是否进行镜像的构造，打镜像，push镜像
//
                    'useCustomImage': true, //使用非标准自定义的镜像, 在jenkins的运行环境中
                    'customImage' : 'docker.dm-ai.cn/devops/base-image-helm-client:dev-0.01', // 使用自定义镜像的地址
                    'execCommand' : 'helm install -f config.yaml --name polyaxon --namespace polyaxon ./polyaxon || helm upgrade polyaxon ./polyaxon -f config.yaml || sleep 6000',
                    'envType'     : 'cpu' //分为gpu 和非gpu的环境
            ],
            'ta-server' : [
                    'servicePort' : '80',
                    'namespace': 'x2',
                    'nodePort' : '31221',
                    'containerPort': '8888',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-server.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
            ],
            'project-tracking-backend': [
                    'servicePort' : '80',
                    'namespace': 'mis',
                    'nodePort' : '30094',
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
            ],
            'x3-tts-serving' : [
                    'servicePort' : '80',
                    'namespace': 'x3',
                    'nodePort' : '30228',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prdd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/x3/tts-serving.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
            ],
            'blackbox-exporter': [
                    'namespace': 'devops',
                    'containerPort': '9115',
                    'domain': '9115',
                    'nodePort': '30915',
                    'kubectlImage': 'devops/base-image-kubectl:dev-0.01'
            ]
    ]
}
