import com.dmai.*
import com.tool.Tools

def call() {
    println('【开始进行构建】')
    pipeline {
        agent {
            kubernetes {
                yaml new JenkinsRunTemplate(conf).getJenkinsRunTemplate(params.DEPLOY_MASTER_PASSWORD, params.DEPLOY_ENV, params)
                cloud 'kubernetes-dev'
                label conf.getAttr('jobName') + '-' + Tools.handleBranchName(conf.getAttr('branchName')) + '-' + conf.getAttr('buildNumber')
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
