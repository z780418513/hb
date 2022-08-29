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
 * @description 菜单实体类
 * @date 2022/8/24
 */
@Data
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 菜单编码
     */
    @TableField("menu_code")
    private String menuCode;

    /**
     * 父节点ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     *节点类型，1=文件夹，2=页面，3=按钮
     */
    @TableField("node_type")
    private Integer nodeType;

    /**
     * 图标地址
     */
    @TableField("icon_url")
    private String iconUrl;

    /**
     * 排序号
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 页面对应的地址
     */
    @TableField("link_url")
    private String linkUrl;

    /**
     * 菜单层级
     */
    @TableField("level")
    private Integer level;

    /**
     * 树id的路径 整个层次上的路径id，逗号分隔，想要找父节点特别快
     */
    @TableField("path")
    private String path;

    /**
     * 是否启用
     */
    @TableField("is_enable")
    private boolean isEnable;

}
