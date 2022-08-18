package com.hb.security.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hb.common.constants.SecurityConstants;
import com.hb.security.token.JwtAuthenticationToken;
import com.hb.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/17
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private TokenService tokenService;
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_FLAG);
        String userKey = request.getHeader(SecurityConstants.USER_KEY_HEADER_FLAG);
        if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(userKey)){
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userKey,token);
            Authentication authenticate = authenticationManager.authenticate(jwtAuthenticationToken);
        }
        filterChain.doFilter(request,response);
    }
}
