package com.hb.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author zhaochengshui
 * @description 令牌认证token
 * @date 2022/8/17
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * username
     */
    private Object username;
    /**
     * token
     */
    private Object token;



    /**
     * 设置未认证 Authentication
     *
     * @param username  username
     * @param token 令牌
     */
    public JwtAuthenticationToken(Object username, Object token) {
        super(null);
        this.username = username;
        this.token = token;
        setAuthenticated(false);
    }

    /**
     * 设置已认证 Authentication
     *
     * @param username        username
     * @param token       令牌
     * @param authorities 权限集合
     */
    public JwtAuthenticationToken(Object username, Object token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        this.token = token;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.token = null;
    }


}
