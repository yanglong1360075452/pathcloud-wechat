package com.wizinno.pathcloud.wechat.util;

import com.google.gson.Gson;
import com.wizinno.pathcloud.wechat.data.dto.MessageDto;
import com.wizinno.pathcloud.wechat.data.dto.TextMessageDto;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

/**
 * Created by LiuMei on 2017-05-19.
 */
public class WechatUtil {

    private static final String appId = "wx9ff41a08eb9923b0";
    private static final String appSecret = "addee241eed7e27dfe95e765984f0ca6";

    //获取网页授权access_token
    private static final String  WebAuthAccessTokenUrl=
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    private static final String AccessTokenUrl =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private static final String SendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    public static void sendMessage(String touser,String content) {
        Gson gson = new Gson();
        AccessToken accessToken = getAccessToken();
        String sendMessageUrl=SendMessageUrl.replace("ACCESS_TOKEN",accessToken.getToken());
        MessageDto messageDto = new MessageDto();
        messageDto.setTouser(touser);
        messageDto.setMsgtype("text");
        TextMessageDto textMessageDto = new TextMessageDto();
        textMessageDto.setContent(content);
        messageDto.setText(textMessageDto);
        JSONObject jsonObject = httpRequest(sendMessageUrl, HttpMethod.POST.toString(), gson.toJson(messageDto));
        System.out.println("返回数据:");
        System.out.println(jsonObject);
    }

    /**
     * 获取接口调用access_token
     *
     * @return
     */
    public static AccessToken getAccessToken(){
        String accessTokenUrl=AccessTokenUrl.replace("APPID",appId).replace("APPSECRET",appSecret);
        JSONObject jsonObject=httpRequest(accessTokenUrl, "GET",null);
        AccessToken accessToken = null;
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                System.out.println("获取token失败 errcode:{"+jsonObject.getInt("errcode")+"}  <br>" +
                        " errmsg:{"+jsonObject.getString("errmsg")+"}");
            }
        }
        return accessToken;
    }

    /**
     * 获取网页授权
     * @param code
     * @return
     */
    public static AccessToken getWebAuthAccessToken(String code){
        String reGetAccessTokenUrl=WebAuthAccessTokenUrl.replace("APPID",appId).replace("SECRET",appSecret).replace("CODE",code);
        JSONObject jsonObject=httpRequest(reGetAccessTokenUrl, "GET",null);
        AccessToken accessToken = null;
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setOpenId(jsonObject.getString("openid"));
                accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                accessToken.setScope(jsonObject.getString("scope"));
                System.setProperty("javax.net.debug", "ssl,handshake");
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                System.out.println("获取token失败 errcode:{"+jsonObject.getInt("errcode")+"}  <br>" +
                        " errmsg:{"+jsonObject.getString("errmsg")+"}");
            }
        }
        return accessToken;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm =  { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            //if ("GET".equalsIgnoreCase(requestMethod))
            httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            System.out.println("Weixin server connection timed out.");
        } catch (Exception e) {
            System.out.println("https request error:{}"+e);
        }
        return jsonObject;
    }
}
