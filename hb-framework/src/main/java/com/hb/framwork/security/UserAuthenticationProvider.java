package com.hb.framwork.security;

import com.hb.framwork.security.service.HbUserDetailsServiceImpl;
import com.hb.system.model.SysUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * 自定义登录验证
 *
 * @author hanbaolaoba
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private HbUserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 查询用户是否存在
        SysUser sysUser = (SysUser) userDetailsService.loadUserByUsername(userName);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, sysUser.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断


        // 角色集合
        Collection<? extends GrantedAuthority> userAuthorities = sysUser.getAuthorities();
        // 进行登录
        return new UsernamePasswordAuthenticationToken(sysUser, password, userAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
