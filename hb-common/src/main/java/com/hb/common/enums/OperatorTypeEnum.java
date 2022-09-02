package com.hb.common.enums;

import lombok.Getter;

/**
 * @author zhaochengshui
 * @description 日志操作类型枚举
 * @date 2022/8/31
 */
@Getter
public enum OperatorTypeEnum implements BaseEnum {

    /*其他*/
    OTHER(0, "其他"),
    /*后台用户*/
    BY_ADMIN(1, "后台用户"),
    /*手机端用户*/
    BY_PHONE(2, "手机端用户"),
    ;

    private final Integer code;
    private final String msg;

    OperatorTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
