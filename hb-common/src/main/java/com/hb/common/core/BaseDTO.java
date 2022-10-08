package com.hb.common.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/21
 */
@Data
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = -4448171502381875176L;

    private Long id;

    @NotNull(message = "当前页码不能为空", groups = {ValidGroup.Page.class})
    private Long current;

    @NotNull(message = "每页条数不能为空", groups = {ValidGroup.Page.class})
    private Long size;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime qStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime qEndTime;

    private String keyword;

    private String remark;
    /**
     * 创建者
     */
    protected String createdBy;

    /**
     * 创建时间
     */
    protected LocalDateTime createdTime;

}
