package com.wizinno.pathcloud.wechat.data.attr;

/**
 * Created by LiuMei on 2017-03-27.
 */
public enum ResearchType {

    Normal(1,"常规染色"),
    Freeze(2,"冰冻预约")
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    ResearchType(Integer _nCode, String _name) {
        this.nCode = _nCode;
        this.name = _name;
    }

    public Integer toCode(){
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static ResearchType valueOf(Integer code) {
        for (ResearchType researchType : ResearchType.values()){
            if (researchType.toCode().equals(code)){
                return researchType;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (ResearchType researchType : ResearchType.values()){
            if (researchType.toCode().equals(code)){
                return researchType.name;
            }
        }
        return null;
    }
}
