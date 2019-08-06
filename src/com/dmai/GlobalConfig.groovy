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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
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
                    'sonarCheck'  : true
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
                    'sonarCheck'  : true
            ],
            'engine-video-extract': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30222',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '4000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '24000m',
                    'memoryLimits' : '8000Mi',
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
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
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
                    'sonarCheck'  : true
            ],
            'monitor-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30237',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '300m',
//                    'memoryRequests' : '500Mi',
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
            ],
            'media-access': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
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
                    'udpPort': [31600, 31800],
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
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
                    'sonarCheck'  : true
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
                    'sonarCheck'  : true
            ],
            'storage-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
                    'cpuRequests' : '1000m',
                    'memoryRequests' : '2000Mi',
                    'replicas' : 2,
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
                    'sonarCheck'  : true

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
                    'sonarCheck'  : true
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
                    'sonarCheck'  : true
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
                    'sonarCheck'  : true
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
                    'sonarCheck'  : true
            ],
            'engine-audio-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30228',
                    'containerPort': '80',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '2000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '12000m',
                    'memoryLimits' : '16000Mi',
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
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
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
                    'gpuLimits' : 1, //一个副本使用几张gpu的卡。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
            ],
            'rdac-frontend': [
                    'servicePort' : '80',
                    'namespace': 'devops',
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
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
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
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
                    'envType'     : 'gpu', //分为gpu 和非gpu的环境
                    'sonarCheck'  : true
            ],
            'xmc2-frontend': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort': '30233',
                    'containerPort': '80',
                    'domain': 'xmc2.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
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
                    'buildArg' : true, //使用自定义dockerfile的时候，是否注入环境变量，VUE_APP_SCENE=xxxx，支持前端一个项目通过参数构建出不同的项目
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : true
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
                    'sonarCheck'  : true
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
                    'sonarCheck'  : true
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'sonarCheck'  : true
            ],
            'resource-service': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31224',
                    'containerPort': '9999',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : false,
            ],
            'ta-admin-server': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31222',
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'auto-deployment-service': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '31226',
                    'containerPort': '8888',
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
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
                    'usePvc': true, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : true, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
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
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'domain': '', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
//                    'cpuRequests' : '1000m',
//                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '500m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd', // 如果参数master 不等于prd，整个构建就失败，---
                    'gitAddress': 'https://gitlab.dm-ai.cn/X3/X3-dispatcher-service.git',
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
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ],
            'ta-prepare-client': [
                    'servicePort' : '80',
                    'namespace': 'x2-ta',
                    'nodePort' : '30127',
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
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-prepare-frontend.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
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
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'app-deploy-platform-deploy-manger': [
                    'servicePort' : '80',
                    'namespace': 'devops',
                    'nodePort': '30102',
                    'containerPort': '5000',
//                    'domain': 'app-deploy-platform.dm-ai.cn', // domain为空，或者没有这条属性，则邮件不发送域名，否则给用户发送域名地址。
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
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
                    'memoryLimits' : '600Mi',
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
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': false, // 是否使用service
                    'makeImage'   : false, // 是否进行镜像的构造，打镜像，push镜像
                    'sonarCheck'  : false
            ],
            'jenkins-test': [
                    'servicePort' : '80',
                    'namespace': 'devops',
                    'nodePort' : '30127',
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
                    'gitAddress': 'https://gitlab.dm-ai.cn/x2/cloud/ta-prepare-frontend.git',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'usePvc': false, // 是否使用pvc的方式挂载额外的数据资源。
                    'useService': true, // 是否使用service
                    'makeImage'   : true, // 是否进行镜像的构造，打镜像，push镜像
                    'useEnvFile'  : false, // 是否使用git仓库deployment下的.env的内容来给容器注入环境变量。
                    'sonarCheck'  : true
            ]
    ]
}
