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
            'storage-service': [
                    'namespace': 'xmc2',
                    'nodePort' : '30220',
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
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'configMapFile-dev': '''
const UUID = require('uuid');
const config = {
    PORT: 3000,
    serviceName: 'storage-service',
    uuid: UUID.v1().replace(/-/g, ''),

    ZK_SERVER: '192.168.3.147:2181,192.168.3.148:2181,192.168.3.149:2181',
    KAFKA_BROKERS: '192.168.3.147:9092,192.168.3.148:9092,192.168.3.149:9092',


    log: {
        level: 'debug',
        logDir: 'logs',
        serviceName: 'storage-service',
        env: 'dev'
    },

    TOPIC: {
        cudSuffix: 'XMC2-CUD',
        summaryInfo: 'XMC2-CUD-SUMMARY-INFO',
        storageCB: 'XMC2-STORAGE-CALLBACK',
    },
    KAFKA_GID: 'COMMON_STORAGE_CLIENT',

    DB: {
         URI: 'mongodb://xmc2-test:c61b071c4b920b@192.168.3.136:27500,192.168.3.137:27500,192.168.3.138:27500/xmc2-test?authSource=xmc2-test',
        dbName: 'xmc2-test'
    },

    COLLS: {
        summaryInfo: 'summary-info',
        metaMediaRelationship: 'meta-media-relationship',
        metaSchools: 'meta-schools',
        metaCameras: 'meta-cameras',
        metaRecords: 'meta-records',
    },


    REDIS_OPTIONS: {

        host: 'redis',
        port: 6379
    },

};

console.log('加载配置', config);
module.exports = config;
''',
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
            'stat-service': [
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
                    'useConfigMap': true, //是否使用configmap
                    'configMapName': 'config.js', //是否使用configmap
                    'configMapFile-dev' : '''
const UUID = require('uuid');
const config = {
    PORT: 3000,
    serviceName: 'storage-service',
    uuid: UUID.v1().replace(/-/g, ''),

    ZK_SERVER: '192.168.3.147:2181,192.168.3.148:2181,192.168.3.149:2181',
    KAFKA_BROKERS: '192.168.3.147:9092,192.168.3.148:9092,192.168.3.149:9092',


    log: {
        level: 'debug',
        logDir: 'logs',
        serviceName: 'storage-service',
        env: 'dev'
    },

    TOPIC: {
        cudSuffix: 'XMC2-CUD',
        summaryInfo: 'XMC2-CUD-SUMMARY-INFO',
        storageCB: 'XMC2-STORAGE-CALLBACK',
    },
    KAFKA_GID: 'COMMON_STORAGE_CLIENT',

    DB: {
         URI: 'mongodb://xmc2-test:c61b071c4b920b@192.168.3.136:27500,192.168.3.137:27500,192.168.3.138:27500/xmc2-test?authSource=xmc2-test',
        dbName: 'xmc2-test'
    },

    COLLS: {
        summaryInfo: 'summary-info',
        metaMediaRelationship: 'meta-media-relationship',
        metaSchools: 'meta-schools',
        metaCameras: 'meta-cameras',
        metaRecords: 'meta-records',
    },


    REDIS_OPTIONS: {

        host: 'redis',
        port: 6379
    },

};

console.log('加载配置', config);
module.exports = config;
''', // master主干的configmap内容
                    'svcType' : 'NodePort', // ['ClusterIP', 'NodePort', 'None']
                    'codeLanguage' : 'node', // 临时的，默认是【js,node,golang,java,php,python】
                    'k8sKind': 'deployment' // 部署的服务的类型
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
                    'dev': 'dev', // dev分支部署到开发环境
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
            'org-frontend': [
                    'nodePort': '30091',
                    'namespace': 'mis'
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
                    'dev': 'dev', // dev分支部署到开发环境
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
                    'dev': 'dev', // dev分支部署到开发环境
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
