import groovy.text.*
import java.io.*

def call() {
    println("test")
}

def successBody(jobName) {
//    return """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
//<p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>
    return """Job build successful. Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/""" + jobName + """/detail/${env.BRANCH_NAME}/${env.BUILD_NUMBER}/pipeline"""
}

def faildBody(jobName) {
//    return """
////<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
////<p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>
////"""
    return """Job build faild. Address : Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/""" + jobName + """/detail/${env.BRANCH_NAME}/${env.BUILD_NUMBER}/pipeline"""
}
//
//def getBuildAddress() {
//    return """http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/${env.JOB_NAME}}/detail/${env.BRANCH_NAME}/${env.BUILD_NUMBER}/pipeline"""
//}

def showEnv(env, String buildResult) {
    def text = """Job build ${buildResult}. Address : http://jenkins.ops.dm-ai.cn/blue/organizations/jenkins/${jobName}/detail/${branchName}/${buildNumber}/pipeline"""
    def template = new groovy.text.StreamingTemplateEngine().createTemplate(text)
    def binding = [
            'jobName' : env.JOB_NAME.split("/")[0],
            'branchName' : env.BRANCH_NAME,
            'buildNumber' : env.BUILD_NUMBER,
            'buildResult': buildResult,
    ]
    String response = template.make(binding)
    return response
}


