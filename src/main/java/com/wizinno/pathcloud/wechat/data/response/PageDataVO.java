package com.wizinno.pathcloud.wechat.data.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuMei on 2017-05-25.
 *
 * 分页数据基类
 */
public class PageDataVO extends ResponseVO {

    public PageDataVO(Integer page, Integer length, Long total, Object data){
        super();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("page", page);
        dataMap.put("length", length);
        dataMap.put("total", total);
        dataMap.put("data", data);

        this.setData(dataMap);
    }

    public PageDataVO(Integer page, Long total, Object data){
        super();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("page", page);
        dataMap.put("length", 10);
        dataMap.put("total", total);
        dataMap.put("data", data);

        this.setData(dataMap);
    }

}
