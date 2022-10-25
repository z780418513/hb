package com.hb.common.enums;

import lombok.Getter;

/**
 * @author zhaochengshui
 * @description 用户注册枚举类
 * @date 2022/10/24
 */
@Getter
public enum RegisterTypeEnum implements BaseEnum {
    /**
     * 用户名密码注册
     */
    USERNAME_PASSWORD(1, "用户名密码"),
    /**
     * 手机号注册
     */
    MOBILE(2, "手机号"),

    ;

    private final Integer code;
    private final String msg;

    RegisterTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
