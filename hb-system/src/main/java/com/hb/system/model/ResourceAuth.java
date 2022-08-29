package com.hb.system.model;

import lombok.Data;

/**
 * @author zhaochengshui
 * @description 模块对应角色实体类，用于角色模块的匹配授权
 * @date 2022/8/27
 */
@Data
public class ResourceAuth {

    /**
     * 角色
     */
    private String role;

    /**
     * 模块
     */
    private String module;
}
