package com.hb.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hb.system.mapper.SysUserMapper;
import com.hb.system.model.LoginUser;
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
 * @author zhaochengshui
 * @description UserDetailsService实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = userMapper.selectOne(new LambdaQueryWrapper<LoginUser>().eq(LoginUser::getUsername, username));
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
     * @param roles 角色权限 "admin,guest"
     * @return List<GrantedAuthority>
     */
    private List<GrantedAuthority> grantedAuthorities(String roles) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.isBlank(roles)) {
            return authorities;
        }
        String[] roleList = roles.split(",");
        for (String role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;

    }
}
