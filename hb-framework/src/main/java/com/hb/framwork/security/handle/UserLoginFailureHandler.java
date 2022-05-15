package com.hb.framwork.security.handle;

import com.alibaba.fastjson.JSON;
import com.hb.common.Result;
import com.hb.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录失败处理类
 */
@Component
@Slf4j
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)  {
        // 这些对于操作的处理类可以根据不同异常进行不同处理
        if (exception instanceof UsernameNotFoundException){
            log.info("【登录失败】"+exception.getMessage());
            ServletUtils.renderString(response, Result.error(500, "用户名不存在").toString());

        }
        if (exception instanceof LockedException){
            log.info("【登录失败】"+exception.getMessage());
            ServletUtils.renderString(response, Result.error(500, "用户被冻结").toString());

        }
        if (exception instanceof BadCredentialsException){
            log.info("【登录失败】"+exception.getMessage());
            ServletUtils.renderString(response, Result.error(500, "用户名密码不正确").toString());

        }

        ServletUtils.renderString(response, Result.error(500, "登录失败").toString());
    }
}
