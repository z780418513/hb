package com.hb.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.common.exceptions.BusinessException;
import com.hb.system.dto.MenuDTO;
import com.hb.system.entity.SysMenu;
import com.hb.system.mapper.SysMenuMapper;
import com.hb.system.model.LoginUser;
import com.hb.system.model.LoginUserContextHolder;
import com.hb.system.service.SysMenuService;
import com.hb.system.service.SysRoleService;
import com.hb.system.vo.MenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
            parentVo.setChildren(getChildrenMenus(parentVo, vos));
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
            parentVo.setChildren(getChildrenMenus(parentVo, vos));
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

    @Override
    public int addMenu(MenuDTO dto) {
        Long parentId = dto.getParentId();
        SysMenu parentMenu = baseMapper.selectById(parentId);
        Assert.notNull(parentMenu, "无该父菜单信息");

        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(dto, sysMenu);
        sysMenu.setLevel(parentMenu.getLevel() + 1);
        LoginUser loginUser = LoginUserContextHolder.getLoginUser();
        sysMenu.setCreatedBy(loginUser.getUsername());
        return baseMapper.insert(sysMenu);
    }

    @Override
    public int switchMenu(MenuDTO dto) {
        // 检验并返回sysMenu
        SysMenu sysMenu = checkReturnMenuById(dto.getId());

        sysMenu.setIsEnable(dto.getIsEnable());
        sysMenu.setUpdateBy(LoginUserContextHolder.getLoginUser().getUsername());
        sysMenu.setUpdateTime(LocalDateTime.now());
        return baseMapper.updateById(sysMenu);
    }

    @Override
    public int deleteMenu(Long id) {
        SysMenu menu = baseMapper.selectById(id);
        Assert.notNull(menu, "无该菜单信息");
        // 校验子菜单是否存在，存在不能删除
        List<SysMenu> childrenMenus = getChildrenMenusByPid(id);
        if (CollectionUtils.isNotEmpty(childrenMenus)) {
            throw new BusinessException("该菜单还有子菜单，不能删除");
        }

        return baseMapper.deleteById(id);
    }

    @Override
    public List<SysMenu> getChildrenMenusByPid(Long pid) {
        List<SysMenu> menus = baseMapper.selectList(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, pid));
        return menus;
    }

    @Override
    public int updateMenu(MenuDTO dto) {
        // 检验并返回sysMenu
        SysMenu sysMenu = checkReturnMenuById(dto.getId());
        BeanUtils.copyProperties(dto, sysMenu);
        sysMenu.setUpdateBy(LoginUserContextHolder.getLoginUser().getUsername());
        sysMenu.setUpdateTime(LocalDateTime.now());
        return baseMapper.updateById(sysMenu);
    }

    private SysMenu checkReturnMenuById(Long id) {
        SysMenu sysMenu = baseMapper.selectById(id);
        if (sysMenu == null) {
            throw new BusinessException("menu don`t exist ...");
        }
        return sysMenu;
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
                childrenMenu.setChildren(getChildrenMenus(childrenMenu, allMenus));
            }
        }
        return childrenMenus;
    }


}
