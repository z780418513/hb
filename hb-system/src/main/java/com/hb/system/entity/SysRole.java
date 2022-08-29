package com.hb.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hb.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhaochengshui
 * @description 角色实体类
 * @date 2022/8/24
 */
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("description")
    private String description;
}
