package com.hb.security.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hb.common.constants.SecurityConstants;
import com.hb.common.utils.RedisUtils;
import com.hb.security.token.JwtAuthenticationToken;
import com.hb.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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
    @Resource
    private RedisUtils redisUtils;
    public final String loginUrl = "/user/login";
    public final String isPost = "POST";

    /**
     * 有token就校验token，没有直接放行
     * token中只存一个username，解密后获得，放在redis中，如果过期了就放行
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 登录请求放行
        if (loginUrl.equals(request.getRequestURI()) && isPost.equals(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_FLAG);
        if (StringUtils.isNotBlank(token)) {
            // 获得token中的username,并获得登录用户信息
            String uuid = tokenService.getUserNameFromToken(token);
            Object loginUser = redisUtils.get(SecurityConstants.TOKEN_REDIS_PREFIX + uuid);
            if (Objects.isNull(loginUser)) {
                filterChain.doFilter(request, response);
                return;
            }
            // 认证token
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(uuid, token, loginUser);
            Authentication authenticate = authenticationManager.authenticate(jwtAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }
        filterChain.doFilter(request, response);
    }
}
