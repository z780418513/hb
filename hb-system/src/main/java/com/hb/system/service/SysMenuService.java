package com.hb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hb.system.dto.MenuDTO;
import com.hb.system.entity.SysMenu;
import com.hb.system.vo.MenuVo;

import java.util.List;

/**
 * @author zhaochengshui
 * @description 菜单service
 * @date 2022/8/24
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取子菜单
     *
     * @param parentMenuId 父菜单id
     * @return List<MenuVo>
     */
    List<MenuVo> getChildrenMenusId(Long parentMenuId);

    /**
     * 树形显示菜单
     *
     * @return List<MenuVo>
     */
    List<MenuVo> getAllTreeMenu();

    /**
     * 根据 用户id 获得 用户菜单
     *
     * @param userId 用户ID
     * @return List<MenuVo>
     */
    List<MenuVo> getTreeMenuByUserId(Long userId);


    /**
     * 根据 角色名 获得 用户菜单
     *
     * @param roleName 角色名
     * @return List<MenuVo>
     */
    List<MenuVo> getMenuByRoleName(String roleName);

    /**
     * 新增菜单
     *
     * @param dto
     * @return
     */
    int addMenu(MenuDTO dto);

    /**
     * 开启禁用菜单
     *
     * @param dto 菜单
     * @return
     */
    int switchMenu(MenuDTO dto);

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    int deleteMenu(Long id);

    /**
     * 根据父id查询子菜单
     *
     * @param pid 父id
     * @return List<SysMenu>
     */
    List<SysMenu> getChildrenMenusByPid(Long pid);

    /**
     * 更新菜单信息
     *
     * @param dto
     */
    int updateMenu(MenuDTO dto);
}
