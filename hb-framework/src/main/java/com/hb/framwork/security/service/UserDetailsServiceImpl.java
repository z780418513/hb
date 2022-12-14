package com.hb.framwork.security.service;


import com.hb.system.mapper.SysUserMapper;
import com.hb.system.model.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hanbaolaoba
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 密码编码
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        // 授权
        user.setAuthorities(grantedAuthorities(user.getRoles()));
        return user;
    }

    /**
     * 自定义实现权限转换
     *
     * @param roles 角色权限 "admin;guest"
     * @return List<GrantedAuthority>
     */
    public static List<GrantedAuthority> grantedAuthorities(String roles) {
        String[] roleList = roles.split(";");
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;

    }
}
