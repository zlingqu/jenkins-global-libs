import com.dmai.*
import com.tool.Tools

def call(Map map, env) {
    String appName
    boolean containsKey = map.containsKey('appName') //containsKey是否包含某个key
    if (containsKey) {
        appName = map.get('appName')
    } else {
        appName = 'common-build-name'
    }

    Conf conf = new Conf(this, appName, map)

    // 把用户设置的全局的属性，加入到默认的全局的设置当中
    conf.setUserAttr(map)

    // 注入jenkins的环境变量到全局的Conf
    conf.setJenkinsAttrToConf(env, currentBuild)

    def branchName = conf.getAttr('branchName') ? conf.getAttr('branchName') : ''

    println('【开始进行构建】')
    pipeline {
        parameters {
            string(name: 'BRANCH_NAME', defaultValue: branchName, description: '代码分支名')
            string(name: 'DEPLOY_MASTER_PASSWORD', defaultValue: 'please-input-password', description: '部署master分支请找运维人员输入密码自动部署')
            choice(name: 'DEPLOY_ENV', choices: deployEnv, description: '部署的环境，目前支持：prd/dev/test/stage等')
        }
        agent {
            kubernetes {
                yaml new JenkinsRunTemplate(conf).getJenkinsRunTemplate(params.DEPLOY_MASTER_PASSWORD, params.DEPLOY_ENV, params)
                cloud 'kubernetes-dev'
                label Tools.handleBranchName(conf.getAttr('branchName'))
                defaultContainer 'jnlp'
                namespace 'devops'
                inheritFrom 'base-template'
            }
        }

        
        options {
            timestamps() //日志会显示时间
            timeout(time: 1, unit: 'HOURS') // 设置任务的超时时间为1个小时。
            buildDiscarder(logRotator(numToKeepStr: '200')) //保留最近200个构建
        }

        stages {
            stage('编译') {

                steps {
                    container('compile') {
                        script {
                            sh 'pwd && ls -lh'
                            sh 'mvn -Dmaven.test.skip=true deploy'
                            sh 'pwd && ls -lh'
                            
                        }
                    }
                }
            }

            stage('push jar') {
                steps {
                    container('compile') {
                        script {
                            def pom = readMavenPom file: 'pom.xml'
                            nexusArtifactUploader(artifacts: [[artifactId: "${pom.artifactId}",
                                           classifier: '',
                                           file: "./target/${pom.artifactId}-${pom.version}.${pom.packaging}",
                                           type: "${pom.packaging}"]],
                              credentialsId: 'nexus',
                              groupId: "${pom.groupId}",
                              nexusUrl: 'http://nexus.dm-ai.cn/',
                              nexusVersion: 'nexus3',
                              protocol: 'https',
                              repository: "${pom.groupId}",
                              version: "${pom.version}")
                        }
                    }
                }

            }
        }
    }
}
