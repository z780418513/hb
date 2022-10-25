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
     * 给用户天加对应的角色
     * @param userId 用户id
     * @param roleName 角色名
     */
    void addUserRole(Long userId, String roleName);
}
