package com.hb.security.porvider;

import com.hb.security.token.JwtAuthenticationToken;
import com.hb.service.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;


/**
 * @author zhaochengshui
 * @date 2022/8/17
 */
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private TokenService tokenService;


    /**
     * 验证token
     *
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = String.valueOf(authentication.getCredentials());
        // 校验token是否有效，无效就直接返回
        if (!tokenService.validateToken(token)) {
            return authentication;
        }
        // TODO 权限信息
        String role = "admin";
        return new JwtAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),
                authentication.getDetails(), Collections.singleton(new SimpleGrantedAuthority(role)));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
