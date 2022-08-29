package com.hb.common.enums;

import lombok.Getter;

/**
 * @author zhaochengshui
 * @description 角色枚举类
 * @date 2022/8/26
 */
@Getter
public enum RoleEnum implements BaseEnum {

    /*超级管理员*/
    ADMIN(1, "ROLE_ADMIN", "超级管理员"),
    USER(2, "ROLE_USER", "普通用户"),
    GUEST(3, "ROLE_GUEST", "游客"),

    ;

    private Integer code;
    private String roleName;
    private String msg;

    RoleEnum(Integer code, String roleName, String msg) {
        this.code = code;
        this.roleName = roleName;
        this.msg = msg;
    }



}
