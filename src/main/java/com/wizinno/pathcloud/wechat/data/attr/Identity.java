package com.wizinno.pathcloud.wechat.data.attr;

/**
 * Created by LiuMei on 2017-05-25.
 */
public enum Identity {

    Undergraduate(0,"本科生"),
    Master(1,"硕士研究生"),
    Doctor(2,"博士研究生"),
    PostDoctor(3,"博士后"),
    Clinic(4,"临床医生"),
    Research(5,"研究人员"),
    Technicist(6,"技术人员"),
    Other(7,"其他")
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    Identity(Integer _nCode, String _name) {
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

    public static Identity valueOf(Integer code) {
        for (Identity identity : Identity.values()){
            if (identity.toCode().equals(code)){
                return identity;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (Identity identity : Identity.values()){
            if (identity.toCode().equals(code)){
                return identity.name;
            }
        }
        return null;
    }

}

