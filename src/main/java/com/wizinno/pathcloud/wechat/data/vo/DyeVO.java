package com.wizinno.pathcloud.wechat.data.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-05-27.
 */
public class DyeVO {

    private Long applicantId;

    private List<IhcBlockVO> ihcBlocks;

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public List<IhcBlockVO> getIhcBlocks() {
        return ihcBlocks;
    }

    public void setIhcBlocks(List<IhcBlockVO> ihcBlocks) {
        this.ihcBlocks = ihcBlocks;
    }
}
