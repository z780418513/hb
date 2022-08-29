package com.hb.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.common.core.Result;
import com.hb.common.utils.ServletUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaochengshui
 * @description 认证失败拦截器
 * @date 2022/8/27
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Result error;
        if (authException instanceof InsufficientAuthenticationException) {
            error = Result.error("尚未登录", null);
        } else {
            error = Result.error("认证失败", null);
        }
        ServletUtils.renderString(response, new ObjectMapper().writeValueAsString(error));
    }

}
