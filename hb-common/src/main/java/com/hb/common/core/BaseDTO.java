package com.hb.common.core;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime qStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
