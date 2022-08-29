package com.hb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * 获得id获得 用户菜单
     *
     * @param userId 用户ID
     * @return List<MenuVo>
     */
    List<MenuVo> getTreeMenuByUserId(Long userId);
}
