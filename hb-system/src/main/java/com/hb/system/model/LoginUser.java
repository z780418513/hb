package com.hb.system.model;

import lombok.Data;

/**
 * @author zhaochengshui
 */
@Data
public class LoginUser {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色 多个用,分割
     */
    private String roles;

    /**
     * 启用状态
     */
    private boolean enable;


    /**
     * uuid
     */
    private String uuid;

    /**
     * 登录时间(时间戳)
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 登录方式
     */
    private Integer loginType;


}
