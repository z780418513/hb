package com.hb.security.filter;

import com.hb.common.constants.SecurityConstants;
import com.hb.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaochengshui
 * @description 自定义token过滤器
 * @date 2022/8/16
 */
public class TokenAuthenticateFilter extends OncePerRequestFilter {
    public final Logger log = LoggerFactory.getLogger(TokenAuthenticateFilter.class);
    @Resource
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_FLAG);
        // 校验token
        if (!tokenService.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }


}



