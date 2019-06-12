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
                    'nodePort': '30090',
                    'namespace': 'mis'
            ],
            'org-frontend': [
                    'nodePort': '30091',
                    'namespace': 'mis'
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
                    'containerPort': '9093',
                    'domain': '9093',
                    'cpuRequests' : '2000m',
                    'memoryRequests' : '2000Mi',
                    'cpuLimits' : '3500m',
                    'memoryLimits' : '5000Mi',
                    'kubectlImage': 'devops/base-image-kubectl:0.01',
                    'compile': false, // 是否编译
                    'deploy': true, // 是否自动化部署
                    'customDockerfile': true, // 是否使用自定义 dockerfile
                    'customKubernetesDeployTemplate' : true, // 是否使用用户自定义的k8s部署文件，默认文件名为：Deploy-k8s.yml
                    'svcType' : 'ClusterIP', // ['ClusterIP', 'NodePort']
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
