package com.hb.system.model;

import com.hb.common.core.ValidGroup;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zhaochengshui
 * @description 注册用户实体类
 * @date 2022/10/24
 */

@Data
@Getter
@Setter
public class RegisterBody implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 注册用户方式
     *
     * @see com.hb.common.enums.RegisterTypeEnum
     */
    @NotNull(message = "注册类型不能为空",groups = {ValidGroup.Add.class})
    private Integer registerType;

}
