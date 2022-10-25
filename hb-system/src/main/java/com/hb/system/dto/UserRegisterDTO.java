package com.hb.system.dto;

import com.hb.common.core.ValidGroup;
import com.hb.system.model.RegisterBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/10/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegisterDTO extends RegisterBody  {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空",groups = {ValidGroup.Add.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空",groups = {ValidGroup.Add.class})
    private String password;

}
