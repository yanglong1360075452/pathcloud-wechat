package com.wizinno.pathcloud.wechat.util;

import com.wizinno.pathcloud.wechat.domain.UserMapper;
import com.wizinno.pathcloud.wechat.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by LiuMei on 2017-05-25.
 */
@Component
public class CookieUtil {

    @Autowired
    UserMapper userMapper;

    public  String getCookieByOpenId(String openId){
        User user = userMapper.selectByOpenId(openId);
        if(user != null){
            return user.getCookie();
        }else {
            return null;
        }
    }
}
