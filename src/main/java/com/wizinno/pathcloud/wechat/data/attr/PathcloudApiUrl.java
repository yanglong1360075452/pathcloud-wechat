package com.wizinno.pathcloud.wechat.data.attr;

/**
 * Created by LiuMei on 2017-05-24.
 */
public enum  PathcloudApiUrl {

    login(1,"login"),
    logout(2,"logout"),
    checkCookie(3,"wechat"),
    getDepartment(4,"wechat/departments"),
    getUserInfo(6,"wechat/user"),
    createApplication(7,"wechat/application"),
    getDyeType(8,"wechat/dye/type"),
    createDye(9,"wechat/dye"),
    getDyeBlocks(10,"wechat/blocks"),
    cancelApplication(11,"wechat/application/cancel"),
    cancelDye(12,"wechat/dye/cancel"),
    getApplications(13,"wechat/application"),
    checkMessage(14,"wechat/check"),
    updateUser(15,"wechat/user"),
    getBooking(16,"wechat/booking"),
    getApplicationDetail(17,"wechat/application/detail"),
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    PathcloudApiUrl(Integer _nCode, String _name) {
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

    public static PathcloudApiUrl valueOf(Integer code) {
        for (PathcloudApiUrl pathcloudApiUrl : PathcloudApiUrl.values()){
            if (pathcloudApiUrl.toCode().equals(code)){
                return pathcloudApiUrl;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (PathcloudApiUrl pathcloudApiUrl : PathcloudApiUrl.values()){
            if (pathcloudApiUrl.toCode().equals(code)){
                return pathcloudApiUrl.toString();
            }
        }
        return null;
    }
    
}
