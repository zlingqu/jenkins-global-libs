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
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
            ],
            'engine-video-extract': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30222',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '100m',
                    'memoryRequests' : '200Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '8000Mi',
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //挂载的configmap的名称
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'golang', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'anyCf': true, //多个configmap
                    'configMapName': 'config.js', //挂载的configmap的名称
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'c++', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'command': 'command: ["/bin/sh", "-c", "cp -rp /src/debug/* /tmp && cd /tmp && ./MediaGateway"]' //自定义命令行
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
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'anyCf': true, //多个configmap
                    'configMapName': 'config.js', //挂载的configmap的名称
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'c++', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'command': 'command: ["/bin/sh", "-c", "cp -rp /src/debug/* /tmp && cd /tmp && ./MediaAccess"]', //自定义命令行
                    'udpPort': [31600, 31699]
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
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
                    'dev': 'test', // dev分支部署到测试环境
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
                    'storage': true  //是否需要挂载存储
            ],
            'stat-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30221',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 5,
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
            ],
            'engine-audio-process': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30228',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
            ],
            'engine-metric-service': [
                    'servicePort' : '80',
                    'namespace': 'xmc2',
                    'nodePort' : '30229',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
                    'replicas' : 1,
                    'dev': 'test', // dev分支部署到测试环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'configMapName': 'config.yaml', //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'python', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
                    'dev': 'test', // dev分支部署到开发环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
            ],
            'mis-admin-frontend': [
                    'namespace': 'mis',
                    'nodePort': '30092',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'test', // dev分支部署到开发环境
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
            ],
            'mis-org-frontend': [
                    'nodePort': '30093',
                    'namespace': 'mis',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'test', // dev分支部署到开发环境
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
            ],
            'mis-org-backend': [
                    'namespace': 'mis',
                    'nodePort' : '31501',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'test', // dev分支部署到开发环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapFile' : '''
NODE_ENV=prod
PORT=3000
APP_NAME=org

# 企业微信access token获取地址
WX_ACCESS_TOKEN_URL=http://mis-admin-backend:5000/api/open/access_token?type=contact

# LDAP服务器配置
LDAP_URL="ldap://192.168.3.41:389"
LDAP_BASE="dc=dmai,dc=com"
LDAP_USER="cn=mis_bind,ou=apps,dc=dmai,dc=com"
LDAP_PASSWD="Dm@imis19"
LDAP_TIMEOUT=120

# MongoDB配置
MONGODB_CONNECTION="mongodb://dm-mis:c243419c3afc7ece77c@192.168.11.51:27500,192.168.12.51:27500,192.168.13.51:27500/dm-mis?authSource=dm-mis"
''', // master主干的configmap内容
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
            ],
            'mis-admin-backend': [
                    'nodePort': '31500',
                    'namespace': 'mis',
                    'containerPort': '5000',
                    'domain': '5000',
                    'cpuRequests' : '200m',
                    'memoryRequests' : '400Mi',
                    'cpuLimits' : '400m',
                    'memoryLimits' : '1000Mi',
                    'dev': 'test', // dev分支部署到开发环境
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': true, //是否使用configmap
                    'configMapFile': '''
NODE_ENV=prod
PORT=5000
APP_NAME=admin

# MongoDB配置
MONGODB_CONNECTION="mongodb://dm-mis:c243419c3afc7ece77c@192.168.11.51:27500,192.168.12.51:27500,192.168.13.51:27500/dm-mis?authSource=dm-mis"

# 默认每页显示条数
PAGE_SIZE=10

# LDAP服务器配置
LDAP_URL="ldap://192.168.3.41:389"
LDAP_BASE="dc=dmai,dc=com"
LDAP_USER="cn=mis_bind,ou=apps,dc=dmai,dc=com"
LDAP_PASSWD="Dm@imis19"
LDAP_TIMEOUT=120

# 微信请求相关配置
WX_BASE_URL="https://qyapi.weixin.qq.com/cgi-bin"
WX_REQUEST_TIMEOUT=3000
WX_CORP_ID=ww399a98a04dfbcda4
WX_CONTACT_SECRET=gZazZkwuoPcrmli8tu4-h6op6CTnL5o7LvoU8wEPuqA
WX_APPROVAL_SECRET="hFw3-lNcqlD4ilT8YwnAJwk650sElWXyVi8n3EEsgDs"
WX_CHECKIN_SECRET="_kvJUlKh4MKlOoo34L-Fl7DDQEODti4SUSfp319cDQw"
WX_DIAL_SECRET="hqx0u6HFFjLGZMF1a4mx1rUDWIQIUILJ7e3wVeA87fg"
WX_CONFERENCE_SECRET="wFLd7aViBKVoSZTRa291PZWiEptzzauIrC-rH1LnYqQ"
WX_CONFERENCE_AGENTID="1000006"
WX_APPROVAL_AGENTID="3010040"
WX_CHECKIN_AGENTID="3010011"
''',
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment', // 部署的服务的类型
                    'configMapName': 'config.env', //是否使用configmap
            ],
            'service-prometheus' : [
                    'nodePort': '30090',
                    'namespace': 'devops',
                    'containerPort': '9090',
                    'domain': 'http://prometheus.ops.dm-ai.cn',
                    'kubectlImage': 'devops/base-image-kubectl:0.01',
            ],
            'prometheus-alertmanager': [
                    'namespace': 'devops',
                    'nodePort' : '30993',
                    'containerPort': '9093',
                    'domain': '9093',
                    'cpuRequests' : '500m',
                    'memoryRequests' : '1000Mi',
                    'cpuLimits' : '1000m',
                    'memoryLimits' : '1500Mi',
                    'dev': 'dev', // dev分支部署到开发环境
                    'kubectlImage': 'devops/base-image-kubectl:0.01',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'prometheus-alertmanager', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
