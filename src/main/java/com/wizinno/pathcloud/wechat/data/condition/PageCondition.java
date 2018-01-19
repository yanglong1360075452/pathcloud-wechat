package com.wizinno.pathcloud.wechat.data.condition;

/**
 * Created by LiuMei on 2017-05-25.
 *
 * 分页条件基类
 */
public class PageCondition {

    private Integer start;

    private Integer size;

    private String order;

    public PageCondition() {
        this.start = 0;
        this.size = 10;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
