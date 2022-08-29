package com.hb.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.system.entity.SysRole;
import com.hb.system.mapper.SysRoleMapper;
import com.hb.system.service.SysRoleService;
import org.springframework.stereotype.Service;

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
}
