package com.wizinno.pathcloud.wechat.data.dto;

import java.util.List;

/**
 * Created by LiuMei on 2017-05-27.
 */
public class DyeDto {

    private String applyTel;

    private String applyUser;

    private Integer departments;

    private List<IhcBlockDto> ihcBlocks;

    public String getApplyTel() {
        return applyTel;
    }

    public void setApplyTel(String applyTel) {
        this.applyTel = applyTel;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public List<IhcBlockDto> getIhcBlocks() {
        return ihcBlocks;
    }

    public void setIhcBlocks(List<IhcBlockDto> ihcBlocks) {
        this.ihcBlocks = ihcBlocks;
    }
}
