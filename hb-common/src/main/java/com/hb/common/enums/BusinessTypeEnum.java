package com.hb.common.enums;

import lombok.Getter;

/**
 * @author zhaochengshui
 * @description 业务类型枚举
 * @date 2022/8/31
 */
@Getter
public enum BusinessTypeEnum implements BaseEnum {
    /*其他*/
    OTHER(0, "其他"),
    /*新增*/
    INSERT(1, "新增"),
    /*修改*/
    UPDATE(2, "修改"),
    /*删除*/
    DELETED(3, "删除"),
    ;

    private final Integer code;
    private final String msg;

    BusinessTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
