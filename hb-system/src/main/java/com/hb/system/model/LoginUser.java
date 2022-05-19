package com.hb.system.model;

import com.hb.common.vaild.ValidGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * @author hanbaolaoba
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 短信验证码
     */
    private String msgCode;
    /**
     * 登录时间
     */
    private Long loginTime;
    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 权限列表
     */
    private Set<String> permissions;
    /**
     * 登入方式 1=用户名密码,2=手机短信
     */
    private String loginType;

}
