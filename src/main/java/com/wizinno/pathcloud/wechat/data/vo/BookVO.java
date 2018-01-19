package com.wizinno.pathcloud.wechat.data.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-05-26.
 */
public class BookVO {

    private Date freezeStartTime; //预约日期

    private List<Integer> cells;

    private Integer instrumentId;//设备ID

    public Date getFreezeStartTime() {
        return freezeStartTime;
    }

    public void setFreezeStartTime(Date freezeStartTime) {
        this.freezeStartTime = freezeStartTime;
    }

    public List<Integer> getCells() {
        return cells;
    }

    public void setCells(List<Integer> cells) {
        this.cells = cells;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }

}
