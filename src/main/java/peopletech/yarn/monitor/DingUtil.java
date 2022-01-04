package peopletech.yarn.monitor;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.junit.Test;


public class DingUtil {
    private static OkHttpClient client = new OkHttpClient();


//    @Test
//    public void test(){
//        sendToDingDing("test", "信息",
//                "https://oapi.dingtalk.com/robot/send?access_token=811092e50f106a971b02fbcd647fd515d5344909e556f78a7e22c1d4b802e6fe");
//    }

    /**
     * 发送钉钉消息
     * @param title 标题
     * @param message 消息内容
     * @param webhook 钉钉自定义机器人webhook
     * @return
     */
    public static boolean sendToDingDing(String title, String message, String webhook) {
        try {
            JSONObject text = new JSONObject();
            text.put("text", message);
            text.put("title", title);

            JSONObject json = new JSONObject();
            json.put("msgtype", "markdown");
            json.put("markdown", text);
            String jsonString = json.toJSONString();

            String type = "application/json; charset=utf-8";
            RequestBody body = RequestBody.create(MediaType.parse(type), jsonString);
            Builder builder = new Request.Builder().url(webhook);
            builder.addHeader("Content-Type", type).post(body);

            Request request = builder.build();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(String.format("send ding message:%s", string));
            //logger.info("send ding message:{}", string);
            JSONObject res = JSONObject.parseObject(string);
            return res.getIntValue("errcode") == 0;
        } catch (Exception e) {
            e.printStackTrace();
            //logger.error("发送钉钉消息错误！ ", e);
            return false;
        }
    }
}
