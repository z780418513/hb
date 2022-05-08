package com.hb.security;

import com.alibaba.fastjson2.JSON;
import com.hb.core.R;
import com.hb.core.utils.ServletUtils;
import com.hb.system.mapper.SysUserMapper;
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
 * 登录成功拦截器
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String msg = String.format("登入成功：%s", user.getUsername());
        ServletUtils.renderString(response, JSON.toJSONString(R.success(msg)));


    }
}
