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
public class MobileRegisterDTO extends RegisterBody {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空", groups = {ValidGroup.Add.class})
    private String mobile;

    /**
     * 短信验证码
     */
    @NotBlank(message = "短信验证码不能为空",groups = {ValidGroup.Add.class})
    private String smsVerifyCode;
}
