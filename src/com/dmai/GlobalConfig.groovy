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
            'mis-admin-frontend': [
                    'namespace': 'mis',
                    'nodePort': '30090',
                    'containerPort': '80',
                    'domain': '80',
                    'cpuRequests' : '100m',
                    'memoryRequests' : '200Mi',
                    'cpuLimits' : '300m',
                    'memoryLimits' : '500Mi',
                    'compile': true, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': false, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : false, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'useConfigMap': false, //是否使用configmap
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'js', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
            ],
            'org-frontend': [
                    'nodePort': '30091',
                    'namespace': 'mis'
            ],
            'mis-org-backend': [
                    'namespace': 'mis',
                    'nodePort' : '30300',
                    'containerPort': '3000',
                    'domain': '3000',
                    'cpuRequests' : '300m',
                    'memoryRequests' : '500Mi',
                    'cpuLimits' : '800m',
                    'memoryLimits' : '1000Mi',
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
                    'k8sKind': 'deployment' // 部署的服务的类型
            ],
            'mis-admin-backend': [
                    'nodePort': '31500',
                    'namespace': 'mis',
                    'appPort': '5000'
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
