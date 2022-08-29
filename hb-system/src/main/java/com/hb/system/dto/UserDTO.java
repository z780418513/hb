package com.hb.system.dto;

import com.hb.common.core.BaseDTO;
import com.hb.common.core.ValidGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {
    @NotBlank(message = "用户名不能为空", groups = {ValidGroup.Add.class})
    private String username;
    private Boolean enable;
    private String realName;
    private String nickName;
    private String mobile;
    private String email;
}
