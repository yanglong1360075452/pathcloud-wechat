package com.wizinno.pathcloud.wechat.Exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2016/6/24.
 */
public class CustomExceptionCode {
    //与pathcloud相同错误,错误码保持一致
    public static final Integer AccessDenied = 1;
    public static final Integer ParamError = 2;
    public static final Integer UsernameOrPasswordError = 4;
    public static final Integer AuthFailed = 101;
    public static final Integer UnNormalError = 103;
    public static final Integer AccessFail = 104;
    public static final Integer ProjectCode= 105;

    private final static Map<Integer, String> errorMap = new HashMap<>();

    static {
        errorMap.put(AccessDenied, "权限不足");
        errorMap.put(AuthFailed, "认证失败");
        errorMap.put(UsernameOrPasswordError, "用户名或密码错误");
        errorMap.put(UnNormalError, "不正常错误，请联系管理员");
        errorMap.put(AccessFail, "请求失败,请联系管理员");
        errorMap.put(ParamError, "参数错误");
        errorMap.put(ProjectCode, "项目代码为必填项,请完善个人信息");
    }

    public static String getReasonByCode(Integer code, String defaultReason){
        if(errorMap.containsKey(code)){
            return errorMap.get(code);
        }else{
            return defaultReason;
        }
    }
}
