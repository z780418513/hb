package com.hb.web.system.controller;

import com.hb.common.core.Result;
import com.hb.system.service.SysRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaochengshui
 * @description 角色controller
 * @date 2022/8/25
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private SysRoleService roleService;

    /**
     * 查询所有的角色
     *
     * @return Result
     */
    @GetMapping("/list")
    public Result getAllRoles() {
        return Result.success(roleService.getAllRoles());
    }
}
