package com.hb.system.model;

import lombok.Data;

/**
 * @author zhaochengshui
 * @description 登录请求体
 */
@Data
public class LoginBody {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captcha;


}
