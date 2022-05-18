package com.hb.framwork.security.handle;

import com.hb.common.Result;
import com.hb.common.utils.ServletUtils;
import com.hb.framwork.security.service.JwtTokenService;
import com.hb.system.model.LoginUser;
import com.hb.system.model.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功处理类
 * @author hanbaolaoba
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private JwtTokenService jwtTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 生成token
        SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtTokenService.generateToken(user);

        ServletUtils.renderString(response, Result.success("登录成功", token).toString());

    }
}
