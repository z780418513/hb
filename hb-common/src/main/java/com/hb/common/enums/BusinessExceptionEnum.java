package com.hb.common.enums;

import lombok.Getter;

/**
 * 业务异常枚举类
 *
 * @author zhaochengshui
 */
@Getter
public enum BusinessExceptionEnum implements BaseEnum{

    /*数据不存在*/
    INFO_NOT_FOUND(3000, "数据不存在"),
    GET_LOGIN_USER_ERROR(3001, "获取用户信息异常"),
    ;

    private final Integer code;
    private final String msg;

    BusinessExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
