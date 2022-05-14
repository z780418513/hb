package com.hb.framwork.security;

import com.hb.framwork.security.service.HbUserDetailsServiceImpl;
import com.hb.framwork.security.utils.JwtTokenUtil;
import com.hb.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    HbUserDetailsServiceImpl userDetailsService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //从请求头中获取token
        String jwtToken = request.getHeader(jwtTokenUtil.getHeader());
        //token判空
        if (jwtToken != null && StringUtils.isNoneEmpty(jwtToken)) {
            //获取用户姓名
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

            //如果可以正确的从JWT中提取用户信息，并且该用户未被授权
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                SysUser userDetails = (SysUser) userDetailsService.loadUserByUsername(username);
                //检验token的合法性
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    //给使用该JWT令牌的用户进行授权
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    //放入spring security的上下文环境中，表示认证通过
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
        }
        //过滤器链往后继续执行
        filterChain.doFilter(request, response);
    }
}
