package com.cyx.common.resp;


/**
 * 公共返回对象枚举
 *
 * @author: LC
 * @date 2022/3/2 1:44 下午
 * @ClassName: RespBean
 */

public enum RespBeanEnum {

    //通用
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),


//    校验
    TOKEN_NO(601,"没有携带token"),
    TOKEN_EXPIRE(602,"token过期"),
    TOKEN_ERR(603,"token无法续期"),

    TIMEOUT_CAPTCHA(604, "验证码已过期"),
    INVALID_USER(605,"非法用户"),
    INVALID_REQUEST(606,"非法请求"),
    ACCESS_LIMIT_REACHED(607,"请勿频繁点击"),
    PASSWORD_ERROR(608,"密码错误"),

    //登录模块
    REGISTER_SUCCESS(201,"注册成功"),
    LOGIN_SUCCESS(202,"登录成功"),
    LOGIN_ERROR(5001, "用户名或者密码不正确"),
    MOBILE_ERROR(5002, "手机号码格式不正确"),
    BIND_ERROR(5003, "参数校验异常"),
    MOBILE_NOT_EXIST(5004, "手机号码不存在"),
    PASSWORD_UPDATE_FAIL(5005, "更新密码失败"),

    //活动模块
    WELFARE_OVER(6001,"活动结束,请明天再来"),
    WELFARE_SUCCESS(6002,"每天限定一次"),


    TRANFER_ERROR(505,"文件上传解析异常"),
    ERR_PATH(507,"接口地址异常"),

    LOGOUT_SUCCESS(508,"退出成功");


    private final Integer code;
    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    RespBeanEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
