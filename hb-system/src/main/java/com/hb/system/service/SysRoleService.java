package com.hb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hb.system.entity.SysRole;

import java.util.List;

/**
 * @author zhaochengshui
 * @description RoleService
 * @date 2022/8/24
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 查询所有的角色
     *
     * @return List<SysRole>
     */
    List<SysRole> getAllRoles();

    /**
     * 根据用户ID查询所有角色信息
     *
     * @param userId 用户ID
     * @return List<SysRole>
     */
    List<SysRole> getRolesByUserId(Long userId);

    /**
     * 给用户天加对应的角色
     *
     * @param userId   用户id
     * @param roleName 角色名
     */
    void addUserRole(Long userId, String roleName);

    /**
     * 根据userId判断是否是超级管理员
     *
     * @param userId 用户Id
     * @return true = 是
     */
    boolean isAdmin(Long userId);

    /**
     * 根据 roleName 判断是否是超级管理员
     *
     * @param roleName 角色名
     * @return true = 是
     */
    boolean isAdmin(String roleName);
}
