package com.wizinno.pathcloud.wechat.data.condition;

/**
 * Created by LiuMei on 2017-05-25.
 */
public class ApplicantCondition extends PageCondition {

    private String openId;

    private Boolean isSelf;

    public Boolean getSelf() {
        return isSelf;
    }

    public void setSelf(Boolean self) {
        isSelf = self;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
