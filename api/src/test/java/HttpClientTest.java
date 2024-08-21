import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

public class HttpClientTest {
    @Test
    public void testGet() throws Exception {
        //创建HttpClient对象
        //已建httpcltentメ苏
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求对象
//        HttpGet httpGet = new HttpGet("http://localhost:8080/spz/secondHand/item/list");
        HttpGet httpGet = new HttpGet("https://www.gduf.edu.cn/");
        //发送请求，接受响应结果
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //获取服务端返回的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("服务端返回的状态码为:" + statusCode);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("服务端返回的数据为:" + body);
        //关闭资源
        response.close();
        httpClient.close();
    }

    @Test
    public void testPost() throws Exception {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求对象
        HttpPost httpPost = new HttpPost("http://localhost:8080/spz/manager/login");
        // 请求参数 用json构建
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "admin");
        jsonObject.put("password", "123456");
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        //发送请求，接受
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println("服务端返回的状态码为:" + response.getStatusLine().getStatusCode());
        System.out.println("服务端返回的数据为:" + EntityUtils.toString(response.getEntity()));
        //关闭资源
        response.close();
        httpClient.close();
    }
}
