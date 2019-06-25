package com.dmai

class GlobalConfig implements Serializable {
    public Map<String, Map<String, String>> globalConfig = [
            'frontend-test' : [
                    'nodePort': '31377'
            ],
            'work-attendance-frontend': [
                    'nodePort': '30800',
                    'namespace': 'mis'
            ],
            'policy-information-point': [
                    'servicePort' : '80',
                    'namespace': 'cum',
                    'nodePort' : '30801',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '800Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': false // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'sync-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30223',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '1000m',
                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '2000m',
                    'memoryLimits' : '4000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'ui-backend-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30227',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'engine-video-extract': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30222',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '1000m',
                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '6000m',
                    'memoryLimits' : '2000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'media-gateway': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30224',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': false // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'media-access': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30225',
                    'containerPort': '8080',
                    'domain': '8080',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': false // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'dispatcher-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30226',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'storage-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30220',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '1000m',
                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '2000m',
                    'memoryLimits' : '4000Mi',
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。

            ],
            'stat-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30221',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '1000m',
                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '3000m',
                    'memoryLimits' : '5000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'vod-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30230',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'meta-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30231',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'meta-adapter': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30232',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'engine-audio-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30228',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '1000m',
                    'memoryRequests' : '1000Mi',
                    'cpuLimits' : '6000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'engine-image-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30235',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'engine-pipeline-manager': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30234',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'engine-metric-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30229',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '1000m',
                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '6000m',
                    'memoryLimits' : '2000Mi',
                    'replicas' : 1,
                    'dev': 'dev', // dev分支部署到测试环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'xmc2-frontend': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort': '30233',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '800Mi',
                    'dev-domain' : '',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': false // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'mis-admin-frontend': [
                    'servicePort' : '80',
                    'namespace': 'mis',
                    'nodePort': '30092',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': false // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'mis-org-frontend': [
                    'servicePort' : '80',
                    'nodePort': '30093',
                    'namespace': 'mis',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd',
                    'gitAddress': 'https://gitlab.dm-ai.cn/mis/org/org-frontend.git',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
                    'usePvc': false // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'mis-org-backend': [
                    'servicePort' : '80',
                    'namespace': 'mis',
                    'nodePort' : '31501',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
            ],
            'mis-admin-backend': [
                    'servicePort' : '80',
                    'nodePort': '31500',
                    'namespace': 'mis',
                    'containerPort': '5000',
                    'domain': '5000',
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': false, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
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
                    'domain': '9093',
                    'cpuRequests' : '500m',
                    'memoryRequests' : '1000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '1500Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'test': true, // 是否从dev分支部署到测试环境
                    'master': 'prd',
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
                    'usePvc': true // 是否使用pvc的方式挂载额外的数据资源。
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
