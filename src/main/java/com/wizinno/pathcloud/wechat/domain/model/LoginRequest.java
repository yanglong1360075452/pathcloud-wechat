package com.wizinno.pathcloud.wechat.domain.model;

/**
 * Created by LiuMei on 2017-05-19.
 */
public class LoginRequest {

    private String username;
    private String password;
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
