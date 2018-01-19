package com.wizinno.pathcloud.wechat.data.dto;

import java.util.List;

/**
 * Created by LiuMei on 2017-05-27.
 */
public class BookDto {

    private Long freezeStartTime; //预约日期

    private List<Integer> cells;

    private Integer instrumentId;//设备ID

    public Long getFreezeStartTime() {
        return freezeStartTime;
    }

    public void setFreezeStartTime(Long freezeStartTime) {
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
