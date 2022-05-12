package com.hb.system.model;

import com.hb.common.vaild.ValidGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hanbaolaoba
 */
@Data
public class LoginBody {
    @NotBlank(message = "用户名不能为空", groups = {ValidGroup.Search.class})
    private String username;
    @NotBlank(message = "密码不能为空", groups = {ValidGroup.Search.class})
    private String password;
    @NotBlank(message = "uuid不能为空", groups = {ValidGroup.Search.class})
    private String uuid;
    @NotBlank(message = "验证码不能为空", groups = {ValidGroup.Search.class})
    private String captcha;
}