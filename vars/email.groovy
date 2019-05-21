import groovy.text.*
import java.io.*

def call() {
    println("test")
}

def successBody(jobName) {
    return """Job build successful. Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/""" + jobName + """/detail/${env.BRANCH_NAME}/${env.BUILD_NUMBER}/pipeline"""
}

def faildBody(jobName) {
    return """Job build faild. Address : Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/""" + jobName + """/detail/${env.BRANCH_NAME}/${env.BUILD_NUMBER}/pipeline"""
}

def showEnv(env, buildResult) {
    def text = 'Job build $buildResult Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/$jobName/detail/$branchName/$buildNumber/pipeline'
    def binding = [
            'jobName' : '11111',
            'branchName' : env.BRANCH_NAME,
            'buildNumber' : env.BUILD_NUMBER,
            'buildResult': buildResult,
    ]
    def engine = new groovy.text.SimpleTemplateEngine()
    def template = engine.createTemplate(text).make(binding)
    println(template)
}


