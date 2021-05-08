package top.flagshen.robot.Utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import top.flagshen.robot.Entity.DeleteGroupMessageRequest;
import top.flagshen.xsrobot.entity.response.MsgReponse;
import top.flagshen.xsrobot.util.HttpApiUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 150149
 */
public class HttpUtil {

    private static CloseableHttpClient httpClient;

    private static String host="127.0.0.1";
    private static int port=53392;
    private static String secret="xiaoshen";

    public static void deleteGroupMessage(long bot, long group, long id, long flag) throws IOException {
        try{
            DeleteGroupMessageRequest deleteGroupMessageRequest = new DeleteGroupMessageRequest();
            deleteGroupMessageRequest.setBot(1497855934);
            deleteGroupMessageRequest.setGroup(group);
            deleteGroupMessageRequest.setFlag(flag);
            deleteGroupMessageRequest.setId(id);

            String jsonStr = JSON.toJSONString(deleteGroupMessageRequest);
            httpClient = HttpClients.createDefault();
            StringBuilder sb = new StringBuilder();
            sb.append("http://").append(host).append(":").append(port).append("/").append("/delete_group_message");
            HttpPost request = new HttpPost(sb.toString());
            request.setHeader("Authorization", secret);

            StringEntity entity = new StringEntity(jsonStr, StandardCharsets.UTF_8);
            entity.setContentType("application/json");
            request.setEntity(entity);
            String json="";
            json = (String)httpClient.execute(request, new BasicResponseHandler());

            httpClient.close();
        }catch (Throwable ignored) {}

    }

}
