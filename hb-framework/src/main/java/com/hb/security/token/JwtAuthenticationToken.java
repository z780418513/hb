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
     * 用户名
     */
    private final Object principal;
    /**
     * 密码
     */
    private Object credentials;


    /**
     * 设置未认证 Authentication
     *
     * @param userKey 用户key
     * @param token   令牌
     */
    public JwtAuthenticationToken(Object userKey, Object token) {
        super(null);
        this.principal = userKey;
        this.credentials = token;
        setAuthenticated(false);
    }

    /**
     * 设置已认证 Authentication
     *
     * @param userKey     用户key
     * @param token       令牌
     * @param authorities 权限集合
     */
    public JwtAuthenticationToken(Object userKey, Object token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = userKey;
        this.credentials = token;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
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
        this.credentials = null;
    }


}
