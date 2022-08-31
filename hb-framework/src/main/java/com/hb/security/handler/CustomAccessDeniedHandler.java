package com.hb.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.common.core.Result;
import com.hb.common.utils.ServletUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户授权认证失败后，会抛AccessDeniedException异常，该拦截器会捕获到，进行处理，将响应信息返回
 *
 * @author zhaochengshui
 * @description 授权失败处理器
 * @date 2022/8/27
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        Result error = Result.error("需要授权", null);
        ServletUtils.renderString(response, new ObjectMapper().writeValueAsString(error));

    }
}
