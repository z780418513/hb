package com.hb.system.model;

import com.hb.common.core.ValidGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhaochengshui
 */
@Data
public class LoginBody {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = {ValidGroup.Search.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {ValidGroup.Search.class})
    private String password;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 验证码
     */
    private String captcha;
}
