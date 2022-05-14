package com.hb.framwork.security.service;

import com.hb.framwork.security.utils.JwtTokenUtil;
import com.hb.system.model.SysUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JwtAuthService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private HbUserDetailsServiceImpl userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 登录并获得token
     *
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password) {
        //使用用户名密码进行登录验证
        UsernamePasswordAuthenticationToken upToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //生成JWT
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(sysUser);
        return token;
    }

    /**
     * 刷新token
     *
     * @param oldToken
     * @return
     */
    public String refreshToken(String oldToken) {
        if (!jwtTokenUtil.isTokenExpired(oldToken)) {
            return jwtTokenUtil.refreshToken(oldToken);
        }
        return null;
    }
}
