package com.wizinno.pathcloud.wechat.data.attr;

/**
 * Created by LiuMei on 2017-05-25.
 * 科研申请--课题类型
 */
public enum TaskType {

    NationalNatural(0,"国家自然科学基金"),
    NationalScience(1,"国家科技部课题"),
    Province(2,"省部级计划课题"),
    Horizontal(3,"横向课题"),
    Other(4,"其他")
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    TaskType(Integer _nCode, String _name) {
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

    public static TaskType valueOf(Integer code) {
        for (TaskType taskType : TaskType.values()){
            if (taskType.toCode().equals(code)){
                return taskType;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (TaskType taskType : TaskType.values()){
            if (taskType.toCode().equals(code)){
                return taskType.name;
            }
        }
        return null;
    }

}
