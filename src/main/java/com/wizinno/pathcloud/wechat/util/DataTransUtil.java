package com.wizinno.pathcloud.wechat.util;

import com.google.gson.Gson;
import com.wizinno.pathcloud.wechat.Exception.CustomException;
import org.json.JSONObject;

/**
 * Created by LiuMei on 2017-05-26.
 */
public class DataTransUtil <T>{

    public static <T> T transObject(String string,Class T) throws CustomException{
        JSONObject jsonObject = new JSONObject(string);
        Integer code = Integer.valueOf(jsonObject.get("code").toString());
        T result;
        if(code.equals(0)){
          Object data = jsonObject.get("data");
            Gson gson = new Gson();
           result = (T) gson.fromJson(data.toString(),T);
        }else {
            throw new CustomException(code,jsonObject.get("reason").toString());
        }
       return result;
    }

}
