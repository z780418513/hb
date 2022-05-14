package com.hb.framwork.security;

import com.alibaba.fastjson2.JSON;
import com.hb.common.Result;
import com.hb.common.utils.ServletUtils;
import com.hb.framwork.security.utils.JwtTokenUtil;
import com.hb.system.model.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功处理类
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private JwtTokenUtil jwtTokenUtil;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 生成token
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtTokenUtil.generateToken(sysUser);

        ServletUtils.renderString(response, Result.success("登录成功", token).toString());

    }
}
