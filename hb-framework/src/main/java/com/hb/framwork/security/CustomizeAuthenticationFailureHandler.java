package com.hb.framwork.security;

import com.alibaba.fastjson.JSON;
import com.hb.common.Result;
import com.hb.common.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录失败拦截器 登入失败后执行
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)  {
        ServletUtils.renderString(response, JSON.toJSONString(Result.error(exception.getMessage())));
    }
}
