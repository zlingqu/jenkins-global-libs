// test java http
//URL url = new URL("http://service-k8s-app-status-check.dm-ai.cn/api/v1/app_status_check")
//HttpURLConnection conn = (HttpURLConnection) url.openConnection()
//conn.setRequestMethod("POST")
//conn.setRequestProperty("Content-Type", "application/json")
//conn.doOutput = true
//def writer = new OutputStreamWriter(conn.outputStream)
//writer.write("{ \n" +
//        "\t\"kubernetes_env\": \"test\", \n" +
//        "\t\"kubernetes_pods_name\": \"xmc-backend-service\", \n" +
//        "\t\"namespace\": \"xmc\"\n" +
//        "}")
//writer.flush()
//writer.close()
//conn.connect()
//def respText = conn.content.text
//println(respText == "111")
//println("http://192.168.3.29:8082/eng-team-models/XMC/xmc_models.git".replace("http://", ""))
//println("git@192.168.3.29:eng-team-models/XMC/xmc_models.git".replace("http://", ""))
//
//// groovy 读取文件, 一次性读取
////String fileContents = new File('/Users/zuoshenglo/goland-workspace/src/devops-dm-ai/deployment/devops/dev/jenkins-test/deploy.yml').getText('UTF-8')
////println(fileContents.replaceAll('JENKINS_DEPLOY_IMAGE_ADDRESS', "wocaowuqing"))
//
//File file0 = new File('1/Users/zuoshenglo/goland-workspace/src/devops-dm-ai/deployment/devops/dev/jenkins-test/deploy.yml');
////File file =
//if(file0.exists()) {
//    println("bucunzai")
//}
//
//if ('x2.dm-ai.cn'.indexOf('deploy-env')) {
//    println(1111)
//}
//
//println('x2.dm-ai.cn111111'.indexOf('deploy-env'))
// 测试post 请求并对请求的结果进行解析
import groovy.json.JsonSlurper
private String getServiceAppStatusV1Url() {
    return String.format('''http://service-k8s-app-status-check-v1.devops.dev.dm-ai.cn/api/v1/pods-status?env=%s&namespace=%s&appName=%s''', 'dev', 'devops', 'jenkins-test')
}

private Map getServiceAppStatusV1() {
    String message = "";
    URL url = new URL(this.getServiceAppStatusV1Url())
    HttpURLConnection conn = (HttpURLConnection) url.openConnection()
    conn.setRequestMethod("GET")
    conn.connect()
    if (conn.getResponseCode() == 200) {
        InputStream inputStream=conn.getInputStream();
        byte[] data=new byte[1024];
        StringBuffer sb1=new StringBuffer();
        int length=0;
        while ((length=inputStream.read(data))!=-1){
            String s=new String(data, 0,length);
            sb1.append(s);
        }
        message=sb1.toString();
        inputStream.close();
    }
    conn.disconnect()
    def jsonSlurper = new JsonSlurper()
    def object = jsonSlurper.parseText(message)
    assert object instanceof Map
    return object
}

def deployInfo = getServiceAppStatusV1()

println(deployInfo.res)
println(deployInfo.status)