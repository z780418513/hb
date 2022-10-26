package com.hb.web.system.controller;

import com.hb.common.core.Result;
import com.hb.system.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public Result getMenuByPid(@RequestParam(value = "pid") Long menuId) {
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

    /**
     * 根据用户ID查询用户对应菜单
     *
     * @param userId
     * @return
     */
    @GetMapping("/{id}")
    public Result getMenus(@PathVariable("id") Long userId) {
        return Result.success(menuService.getTreeMenuByUserId(userId));
    }
}
