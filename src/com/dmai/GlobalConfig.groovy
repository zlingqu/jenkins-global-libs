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
                    'kubectlImage': 'devops/base-image-kubectl:0.01'
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
