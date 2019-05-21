def call() {
    println("test")
}

def successBody() {
    return """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
<p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>
"""
}

def faildBody() {
    return """
<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
<p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>
"""
}