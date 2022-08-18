package com.hb.security.porvider;

import com.hb.common.utils.RedisUtil;
import com.hb.security.token.JwtAuthenticationToken;
import com.hb.service.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author zhaochengshui
 * @date 2022/8/17
 */
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private TokenService tokenService;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (tokenService.validateToken((String) authentication.getCredentials())){
            return new JwtAuthenticationToken(authentication.getPrincipal(),authentication.getCredentials());
        }

        return new JwtAuthenticationToken("121212121","1212");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
