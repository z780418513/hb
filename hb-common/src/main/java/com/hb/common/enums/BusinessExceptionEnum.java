package com.hb.common.enums;

import lombok.Getter;

/**
 * 业务异常枚举类
 *
 * @author zhaochengshui
 */
@Getter
public enum BusinessExceptionEnum implements BaseEnum {
    /*验证码错误*/
    CODE_OR_PASSWORD_ERROR(2000, "验证码错误"),
    /*密码错误*/
    PASSWORD_ERROR(2001, "密码错误"),
    /*令牌验证失败*/
    TOKEN_VALID_FAIL(2002, "令牌验证失败"),
    /*非法令牌*/
    TOKEN_ILLEGALITY(2003, "非法令牌"),
    /*令牌过期*/
    TOKEN_IS_EXPIRE(2004, "令牌过期"),
    ;

    private final Integer code;
    private final String msg;

    BusinessExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
