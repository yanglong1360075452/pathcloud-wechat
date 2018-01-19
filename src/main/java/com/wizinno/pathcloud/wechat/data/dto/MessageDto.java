package com.wizinno.pathcloud.wechat.data.dto;

/**
 * Created by LiuMei on 2017-06-05.
 */
public class MessageDto {

    private String touser;

    private String msgtype;

    private TextMessageDto text;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public TextMessageDto getText() {
        return text;
    }

    public void setText(TextMessageDto text) {
        this.text = text;
    }
}
