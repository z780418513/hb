package com.hb.framwork.security.provider;

import com.hb.framwork.security.service.UserDetailsServiceImpl;
import com.hb.framwork.security.token.MobileCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 手机短信登入验证提供者
 *
 * @Author hanbaolaoba
 * @Date 2022/05/19  09:29
 **/
@Component
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {


    /**
     * TODO 认证逻辑
     * 验证
     * 未认证authentication ==>  认证通过authentication
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 根据手机号查询用户信息

        MobileCodeAuthenticationToken authenticationToken = new MobileCodeAuthenticationToken(
                "phone", "msgCode", UserDetailsServiceImpl.grantedAuthorities("ROLE_ADMIN"));
        return authenticationToken;
    }


    /**
     * 支持 MobileCodeAuthenticationToken 才能执行this.authenticate
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobileCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
