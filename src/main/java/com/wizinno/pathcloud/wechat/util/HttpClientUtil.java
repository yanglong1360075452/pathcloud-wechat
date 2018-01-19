package com.wizinno.pathcloud.wechat.util;

import com.wizinno.pathcloud.wechat.Exception.CustomException;
import com.wizinno.pathcloud.wechat.Exception.CustomExceptionCode;
import com.wizinno.pathcloud.wechat.data.Config;
import com.wizinno.pathcloud.wechat.data.attr.PathcloudApiUrl;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

/**
 * Created by LiuMei on 2017-05-22.
 */
@Component
public class HttpClientUtil {
    private Logger logger = Logger.getLogger(HttpClientUtil.class);
    private String charset = "UTF-8";
    private String pathcloudUrl = Config.pathcloudBaseUrl;

    public Map<String, Object> login(String username, String password) throws CustomException {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        Map<String, Object> map = new HashMap<>();
        String result = null;
        try {
            httpClient = new SSLClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpPost = new HttpPost(pathcloudUrl + PathcloudApiUrl.login.toString());
        //设置参数
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));

        if (list.size() > 0) {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(list, charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpPost.setEntity(entity);
        }
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Accept", "application/json");
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            String cookie = "";
            Header[] allHeaders = response.getAllHeaders();
            for (Header header : allHeaders) {
                if (header.getName().equals("Set-Cookie")) {
                    cookie = header.getValue();
                    break;
                }
            }
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    try {
                        result = EntityUtils.toString(resEntity, charset);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.debug("ACCESS " + pathcloudUrl + "login   Response:" + result);
                }
                JSONObject resultJson = new JSONObject(result);
                Object code = resultJson.get("code");
                if (Integer.valueOf(code.toString()).equals(0)) {
                    Object data = resultJson.get("data");
                    JSONObject jsonObject = new JSONObject(data.toString());
                    map.put("cookie", cookie);
                    map.put("userId", jsonObject.get("id"));
                } else {
                    map.put("data", resultJson.get("data"));
                }
            } else {
                logger.error(username + "login fail !");
                throw new CustomException(CustomExceptionCode.AccessFail);
            }
        } else {
            throw new CustomException(CustomExceptionCode.AccessFail);
        }
        return map;
    }

    public String doPost(String url, String paramsJson, String cookie) throws CustomException {

        String result = null;

        HttpClient httpClient = null;
        try {
            httpClient = new SSLClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost(pathcloudUrl + url);
        //设置参数
        if (paramsJson != null && paramsJson.length() > 0) {
            StringEntity se = new StringEntity(paramsJson, APPLICATION_JSON);
            httpPost.setEntity(se);
        }
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Cookie", cookie);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null) {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    try {
                        result = EntityUtils.toString(resEntity, charset);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (statusLine.getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                logger.error("ACCESS  " + pathcloudUrl + url + "   POST  Fail   Response:" + response);
                throw new CustomException(CustomExceptionCode.AccessDenied);
            } else {
                logger.error("ACCESS  " + pathcloudUrl + url + "   POST  Fail  Response:" + response);
                throw new CustomException(CustomExceptionCode.AccessFail);
            }
        } else {
            throw new CustomException(CustomExceptionCode.AccessFail);
        }
        logger.debug("ACCESS  " + pathcloudUrl + url + "  POST   Response:" + result);
        return result;
    }

    public String doGet(String url, Map<String, Object> map, String cookie) throws CustomException {
        String result = "";
        String param = "";
        HttpClient httpClient = null;
        try {
            httpClient = new SSLClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List params = new ArrayList();
        if (map != null && map.size() > 0) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> elem = (Map.Entry<String, Object>) iterator.next();
                if (elem.getValue() != null) {
                    params.add(new BasicNameValuePair(elem.getKey(), elem.getValue().toString()));
                }
            }
        }
        if (params != null && params.size() > 0) {
            param = URLEncodedUtils.format(params, charset);
            param = "?" + param;
        }
        HttpUriRequest httpUriRequest = new HttpGet(pathcloudUrl + url + param);
        httpUriRequest.setHeader("Content-Type", "application/json");
        httpUriRequest.setHeader("Accept", "application/json");
        httpUriRequest.setHeader("Cookie", cookie);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpUriRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (httpResponse != null) {
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                try {
                    result = EntityUtils.toString(entity, charset);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataTransUtil.transObject(result, Object.class);
            } else if (statusCode == HttpStatus.SC_FORBIDDEN) {
                logger.error("ACCESS  " + pathcloudUrl + url + param + "   GET Fail   Response:" + httpResponse);
                throw new CustomException(CustomExceptionCode.AccessDenied);
            } else {
                logger.error("ACCESS  " + pathcloudUrl + url + param + "   GET Fail  Response:" + httpResponse);
                throw new CustomException(CustomExceptionCode.AccessFail);
            }
            logger.debug("ACCESS  " + pathcloudUrl + url + param + "   GET   Response:" + result);
        } else {
            throw new CustomException(CustomExceptionCode.AccessFail);
        }
        return result;
    }
}
