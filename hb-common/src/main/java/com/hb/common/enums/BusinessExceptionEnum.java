package com.hb.common.enums;

import lombok.Getter;


/**
 * 业务异常枚举类
 *
 * @author hanbaolaoba
 */
@Getter
public enum BusinessExceptionEnum implements BaseEnum {
    /**
     * 验证码或密码错误
     */
    CODE_OR_PASSWORD_ERROR(1999, "验证码错误"),
    CAPTCHA_OR_UUID_NULL(2000, "验证码或uuid为空");

    private final Integer code;
    private final String msg;

    BusinessExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
