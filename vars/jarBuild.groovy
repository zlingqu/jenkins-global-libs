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

    println('【开始执行任务】')
    pipeline {
        agent {
            kubernetes {
                yaml new JenkinsRunTemplate(conf).getJenkinsRunTemplateOnJar()
                cloud 'kubernetes-dev'
                label 'jar-build'
                namespace 'devops'
            }
        }
        options {
            timestamps() //日志会显示时间
            timeout(time: 1, unit: 'HOURS') // 设置任务的超时时间为1个小时。
            buildDiscarder(logRotator(numToKeepStr: '200')) //保留最近200个构建
        }

        stages {
            stage('编译并上传jar包到nexue') {
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
        }
    }
}
