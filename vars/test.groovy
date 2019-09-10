// test java http
URL url = new URL("http://service-k8s-app-status-check.dm-ai.cn/api/v1/app_status_check")
HttpURLConnection conn = (HttpURLConnection) url.openConnection()
conn.setRequestMethod("POST")
conn.setRequestProperty("Content-Type", "application/json")
conn.doOutput = true
def writer = new OutputStreamWriter(conn.outputStream)
writer.write("{ \n" +
        "\t\"kubernetes_env\": \"test\", \n" +
        "\t\"kubernetes_pods_name\": \"xmc-backend-service\", \n" +
        "\t\"namespace\": \"xmc\"\n" +
        "}")
writer.flush()
writer.close()
conn.connect()
def respText = conn.content.text
println(respText == "111")
