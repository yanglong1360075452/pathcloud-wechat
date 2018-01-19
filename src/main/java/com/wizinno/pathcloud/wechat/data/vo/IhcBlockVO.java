package com.wizinno.pathcloud.wechat.data.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-05-27.
 */
public class IhcBlockVO {

    private String serialNumber;

    private String subId;

    private Integer specialDye;

    private List<String> ihcMarker;

    private String note;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public List<String> getIhcMarker() {
        return ihcMarker;
    }

    public void setIhcMarker(List<String> ihcMarker) {
        this.ihcMarker = ihcMarker;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
