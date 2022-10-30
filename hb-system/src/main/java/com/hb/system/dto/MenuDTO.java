package com.hb.system.dto;

import com.hb.common.core.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/10/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuDTO extends BaseDTO {

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
     * 是否启用
     */
    private Boolean isEnable;
}
