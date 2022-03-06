package com.example.dbmanege.result;

import lombok.Getter;

/**
 * @author niu
 * @Description: 返回类型的枚举值
 * @date 2021/5/514:29
 */

@Getter
public enum ResultCodeEnum {

    SUCCESS(true,0,"成功"),
    UNKNOW_REASON(false,-1,"未知错误"),
    BAD_SQL_GRAMMER(false,-2,"sql语法错误"),
    JSON_PARSE_ERROR(false,-3,"json解析异常"),
    PARAM_ERROR(false,-4,"参数不正确"),
    ERROR_LOGIN(false,-5,"用户名或密码错误"),
    ERROR_REGISTER(false,-7,"注册失败!"),
    SUCCESS_LOGIN(true,0,"登录成功!"),
    SUCCESS_REGISTER(true,0,"注册成功!");

    private Boolean status; //响应是否成功
    private Integer code; //返回码
    private String message; //返回消息

    ResultCodeEnum(Boolean status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
