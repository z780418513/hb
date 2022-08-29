package com.hb.web.system.controller;

import com.hb.common.core.Result;
import com.hb.system.service.SysMenuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author zhaochengshui
 * @description 菜单controller
 * @date 2022/8/24
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private SysMenuService menuService;

    /**
     * 查询所有子菜单(根据父id)
     *
     * @param menuId 父id
     * @return Result
     */
    @GetMapping("/pid")
    public Result getMenuByPid(@Validated @NotBlank(message = "菜单id不能为空") @RequestParam(value = "pid", required = false) Long menuId) {
        return Result.success(menuService.getChildrenMenusId(menuId));
    }

    /**
     * 树形显示菜单
     *
     * @return Result
     */
    @GetMapping("/tree")
    public Result getAllTreeMenu() {
        return Result.success(menuService.getAllTreeMenu());
    }
}
