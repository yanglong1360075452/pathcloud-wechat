package com.wizinno.pathcloud.wechat.quartz;

import com.wizinno.pathcloud.wechat.condition.UserCondition;
import com.wizinno.pathcloud.wechat.data.attr.PathcloudApiUrl;
import com.wizinno.pathcloud.wechat.domain.UserMapper;
import com.wizinno.pathcloud.wechat.domain.model.User;
import com.wizinno.pathcloud.wechat.util.DataTransUtil;
import com.wizinno.pathcloud.wechat.util.HttpClientUtil;
import com.wizinno.pathcloud.wechat.util.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class NotificationTask {

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private UserMapper userMapper;

    /**
     * 申请样本变动查询
     */
    public void sampleNotificationCheck() {
    Date date = new Date();
    Long time = date.getTime()-(1000 * 60 * 30); //30分钟
        UserCondition condition  = new UserCondition();
        condition.setReceived(true);
        List<User> users = userMapper.selectByCondition(condition);
        if (users != null && users.size() > 0) {
            for (User user : users) {
                String cookie = user.getCookie();
                try {
                    httpClientUtil.doGet(PathcloudApiUrl.checkCookie.toString(), null, cookie);
                } catch (Exception e) {
                    Map<String, Object> login = null;
                    try {
                        login = httpClientUtil.login(user.getUsername(), user.getPassword());
                        cookie = login.get("cookie").toString();
                        user.setCookie(cookie);
                        userMapper.update(user);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                String result = httpClientUtil.doGet(PathcloudApiUrl.checkMessage.toString()+"/"+time, null, cookie);
                Object o = DataTransUtil.transObject(result, List.class);
                if(o != null){
                    List<String> pathNos = (List<String>) o;
                    if(pathNos != null && pathNos.size() > 0){
                        for(String pathNo : pathNos ){
                            WechatUtil.sendMessage(user.getOpenId(),"病理号"+pathNo+"制片完成!");
                        }
                    }
                }
            }
        }
    }

}
