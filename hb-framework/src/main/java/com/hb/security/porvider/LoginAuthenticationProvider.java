package com.hb.security.porvider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;


public class LoginAuthenticationProvider extends DaoAuthenticationProvider {


    /**
     * 在原先使用用户名,密码校验之外,增加额外的功能
     *
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        // 继续执行DaoAuthenticationProvider中的方法(用户名,密码校验)
        System.out.println("执行 自定义provider");
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
