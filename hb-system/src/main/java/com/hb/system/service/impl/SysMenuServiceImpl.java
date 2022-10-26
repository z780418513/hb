package com.hb.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.system.entity.SysMenu;
import com.hb.system.mapper.SysMenuMapper;
import com.hb.system.service.SysMenuService;
import com.hb.system.service.SysRoleService;
import com.hb.system.vo.MenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaochengshui
 * @description 菜单服务实现类
 * @date 2022/8/24
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource
    private SysRoleService sysRoleService;

    @Override
    public List<MenuVo> getChildrenMenusId(Long parentMenuId) {
        List<SysMenu> menus = baseMapper.selectList(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getParentId, parentMenuId)
                .orderByAsc(SysMenu::getSort));
        List<MenuVo> menuVos = menus.stream().map(sysMenu -> {
            MenuVo vo = new MenuVo();
            BeanUtils.copyProperties(sysMenu, vo);
            return vo;
        }).collect(Collectors.toList());
        return menuVos;
    }

    @Override
    public List<MenuVo> getAllTreeMenu() {
        List<SysMenu> menus = baseMapper.selectList(Wrappers.<SysMenu>lambdaQuery()
                // 排序 level --> sort
                .orderByAsc(SysMenu::getLevel, SysMenu::getSort));
        List<MenuVo> vos = menus.stream().map(
                sysMenu -> {
                    MenuVo vo = new MenuVo();
                    BeanUtils.copyProperties(sysMenu, vo);
                    return vo;
                }
        ).collect(Collectors.toList());
        List<MenuVo> parentVos = vos.stream().filter(vo -> vo.getParentId() == 0).collect(Collectors.toList());
        for (MenuVo parentVo : parentVos) {
            parentVo.setChildrens(getChildrenMenus(parentVo, vos));
        }
        return parentVos;
    }

    @Override
    public List<MenuVo> getTreeMenuByUserId(Long userId) {
        // 判断用户是否是ADMIN权限，不是的直接返回（只有ADMIN才能）
        List<MenuVo> vos;
        if (sysRoleService.isAdmin(userId)) {
            vos = baseMapper.getMenuWithAdmin();
        } else {
            vos = baseMapper.getMenuByUserId(userId);
        }
        List<MenuVo> parentVos = vos.stream().filter(vo -> vo.getParentId() == 0).collect(Collectors.toList());
        for (MenuVo parentVo : parentVos) {
            parentVo.setChildrens(getChildrenMenus(parentVo, vos));
        }
        return parentVos;
    }

    @Override
    public List<MenuVo> getMenuByRoleName(String roleName) {
        // 超级管理员
        List<MenuVo> vos;
        if (sysRoleService.isAdmin(roleName)) {
            vos = baseMapper.getMenuWithAdmin();
        } else {
            vos = baseMapper.getMenuByRoleName(roleName);
        }
        return vos;
    }

    /**
     * 查询下级子菜单
     *
     * @param parentVo 父菜单对象
     * @param allMenus 所有的菜单集合
     * @return 当前父菜单的子菜单
     */
    public List<MenuVo> getChildrenMenus(MenuVo parentVo, List<MenuVo> allMenus) {
        ArrayList<MenuVo> childrenMenus = new ArrayList<>();
        for (MenuVo vo : allMenus) {
            if (parentVo.getId().equals(vo.getParentId())) {
                childrenMenus.add(vo);
            }
        }
        // 如果当前子菜单有下级子菜单，继续递归查询
        if (CollectionUtils.isNotEmpty(childrenMenus)) {
            for (MenuVo childrenMenu : childrenMenus) {
                childrenMenu.setChildrens(getChildrenMenus(childrenMenu, allMenus));
            }
        }
        return childrenMenus;
    }


}
