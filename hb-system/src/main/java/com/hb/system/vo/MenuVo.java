package com.hb.system.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/24
 */
@Data
public class MenuVo {
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 父节点ID
     */
    private Long parentId;

    /**
     *节点类型，1=文件夹，2=页面，3=按钮
     */
    private Integer nodeType;

    /**
     * 图标地址
     */
    private String iconUrl;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 页面对应的地址
     */
    private String linkUrl;

    /**
     * 菜单层级
     */
    private Integer level;

    /**
     * 树id的路径 整个层次上的路径id，逗号分隔，想要找父节点特别快
     */
    private String path;

    /**
     * 子菜单
     */
    private List<MenuVo> children;

    /**
     * 是否启用
     */
    private boolean isEnable;

    private Long userId;

    private Long roleId;

    private String roleName;
}
