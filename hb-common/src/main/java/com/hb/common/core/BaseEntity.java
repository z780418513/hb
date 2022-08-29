package com.hb.common.core;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author zhaochengshui
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createdBy;

    /**
     * 修改者
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 逻辑删除 1=true 0=false
     */
    @TableLogic
    @TableField("is_deleted")
    private boolean deleted;
}
