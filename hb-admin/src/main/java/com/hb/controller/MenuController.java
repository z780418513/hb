package com.hb.controller;

import com.hb.common.core.Result;
import com.hb.system.dto.MenuDTO;
import com.hb.system.entity.SysMenu;
import com.hb.system.service.SysMenuService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    @GetMapping("/tree/user/{id}")
    public Result getTreeMenuByUserId(@PathVariable("id") Long userId) {
        return Result.success(menuService.getTreeMenuByUserId(userId));
    }

    /**
     * 根据菜单id查询菜单
     *
     * @param menuId 菜单id
     * @return
     */
    @GetMapping("/getById")
    public Result getById(@RequestParam(value = "id") Long menuId) {
        return Result.success(menuService.getById(menuId));
    }


    /**
     * 新增菜单
     *
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public Result addMenu(@RequestBody MenuDTO dto) {
        menuService.addMenu(dto);
        return Result.success();
    }

    /**
     * 修改菜单
     *
     * @param dto
     * @return
     */
    @PutMapping("/update")
    public Result updateMenu(@RequestBody MenuDTO dto) {
        menuService.updateMenu(dto);
        return Result.success();
    }

    /**
     * 开启禁用菜单
     *
     * @param dto
     * @return
     */
    @PutMapping("/switch")
    public Result switchMenu(@RequestBody MenuDTO dto) {
        menuService.switchMenu(dto);
        return Result.success();
    }


    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public Result deleteMenu(@PathVariable("id") Long id) {
        menuService.deleteMenu(id);
        return Result.success();
    }

    /**
     * 根据父id判断是否有子菜单
     *
     * @param pid 父id
     * @return
     */
    @GetMapping("/hasChildren")
    public Result hasChildren(@RequestParam("id") Long pid) {
        List<SysMenu> menus = menuService.getChildrenMenusByPid(pid);
        return Result.success(CollectionUtils.isEmpty(menus));
    }
}
