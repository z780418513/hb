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
    PASSWORD_ERROR(2001, "密码错误");

    private final Integer code;
    private final String msg;

    BusinessExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
