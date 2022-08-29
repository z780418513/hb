package com.hb.common.enums;

import lombok.Getter;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/24
 */
@Getter
public enum SysExceptionEnum implements BaseEnum {

    //--------------------登录相关--------------------
    /*验证码错误*/
    CODE_OR_PASSWORD_ERROR(2000, "验证码错误"),
    /*密码错误*/
    USERNAME_PASSWORD_ERROR(2001, "用户名或密码错误"),
    /*令牌验证失败*/
    TOKEN_VALID_FAIL(2002, "令牌验证失败"),
    /*非法令牌*/
    TOKEN_ILLEGALITY(2003, "非法令牌"),
    /*令牌过期*/
    TOKEN_IS_EXPIRE(2004, "令牌过期"),


    //--------------------校验相关--------------------
    PARAMS_NOT_NULL(3000, "参数不能为空"),

    ;

    private final Integer code;
    private final String msg;

    SysExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
