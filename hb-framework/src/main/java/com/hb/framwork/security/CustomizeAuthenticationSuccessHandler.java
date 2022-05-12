package com.hb.framwork.security;

import com.alibaba.fastjson2.JSON;
import com.hb.common.Result;
import com.hb.common.utils.ServletUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功拦截器,登录成功后执行
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String msg = String.format("登入成功：%s", user.getUsername());
        ServletUtils.renderString(response, JSON.toJSONString(Result.success(msg)));


    }
}
