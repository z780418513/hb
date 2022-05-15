package com.hb.framwork.security.handle;

import com.alibaba.fastjson2.JSON;
import com.hb.common.Result;
import com.hb.common.utils.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户未登录处理类
 */
@Component
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ServletUtils.renderString(response, Result.error(401, "未登录").toString());
    }
}
