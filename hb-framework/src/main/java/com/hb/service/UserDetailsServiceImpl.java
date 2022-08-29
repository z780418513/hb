package com.hb.service;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb.system.entity.SysRole;
import com.hb.system.entity.SysUser;
import com.hb.system.mapper.SysRoleMapper;
import com.hb.system.mapper.SysUserMapper;
import com.hb.system.model.CustomUserDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaochengshui
 * @description UserDetailsService实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysRoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername,username));
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        CustomUserDetails userDetails = new CustomUserDetails();
        BeanUtils.copyProperties(user,userDetails);
        // 密码编码
        userDetails.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        // 授权
        List<SysRole> sysRoles = roleMapper.queryRolesByUserId(user.getId());
        userDetails.setRoles(sysRoles);
        return userDetails;
    }

}
