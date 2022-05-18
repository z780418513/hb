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

    private String username;

    private String password;

    private String uuid;

    private String captcha;

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

}
