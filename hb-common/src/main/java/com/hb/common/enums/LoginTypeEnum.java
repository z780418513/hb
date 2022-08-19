package com.hb.common.enums;

import lombok.Getter;

/**
 * @author zhaochengshui
 * @description 登录方式
 * @date 2022/8/18
 */
@Getter
public enum LoginTypeEnum implements BaseEnum {
    /*手机*/
    PHONE(1, "手机"),
    /*用户名密码*/
    USERNAME_PASSWORD(2, "用户名密码"),
    /*微信*/
    WeChat(3, "微信"),
    ;

    private final Integer code;
    private final String msg;

    LoginTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
