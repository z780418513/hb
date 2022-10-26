package com.hb.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.common.enums.RoleEnum;
import com.hb.common.exceptions.BusinessException;
import com.hb.system.entity.SysRole;
import com.hb.system.mapper.SysRoleMapper;
import com.hb.system.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author zhaochengshui
 * @description RoleServiceImpl
 * @date 2022/8/24
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> getAllRoles() {
        List<SysRole> sysRoles = baseMapper.selectList(Wrappers.<SysRole>lambdaQuery());
        return sysRoles;
    }

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        List<SysRole> sysRoles = baseMapper.queryRolesByUserId(userId);
        return sysRoles;
    }

    @Override
    public void addUserRole(Long userId, String roleName) {
        SysRole sysRoles = baseMapper.selectOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleName, roleName));
        if (sysRoles == null) {
            throw new BusinessException("this role: " + roleName + "not exist");
        }
        // 新增角色
        baseMapper.addUserRole(userId, sysRoles.getId());

    }

    @Override
    public boolean isAdmin(Long userId) {
        List<SysRole> sysRoles = baseMapper.queryRolesByUserId(userId);
        if (CollectionUtils.isEmpty(sysRoles)) {
            return false;
        }
        long count = sysRoles.stream()
                .filter(sysRole -> RoleEnum.ADMIN.getRoleName().equals(sysRole.getRoleName()))
                .count();
        return count > 0;
    }

    @Override
    public boolean isAdmin(String roleName) {
        return RoleEnum.ADMIN.getRoleName().equals(roleName);
    }
}
