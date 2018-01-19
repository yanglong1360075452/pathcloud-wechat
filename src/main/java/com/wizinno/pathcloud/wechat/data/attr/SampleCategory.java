package com.wizinno.pathcloud.wechat.data.attr;

/**
 * 样本类别
 */
public enum SampleCategory {
    Big(1,"大样本"),
    Min(2,"小样本")
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    SampleCategory(Integer _nCode, String _name) {
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

    public static SampleCategory valueOf(Integer code) {
        for (SampleCategory applicationStatus : SampleCategory.values()){
            if (applicationStatus.toCode().equals(code)){
                return applicationStatus;
            }
        }
        return SampleCategory.Big;
    }
}
