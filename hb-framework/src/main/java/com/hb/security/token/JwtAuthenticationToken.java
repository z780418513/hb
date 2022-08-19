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
     * 生成token时的UUID
     */
    private Object uuid;
    /**
     * token
     */
    private Object token;
    /**
     * loginUser信息(包含uuid)
     */
    private Object loginUser;


    /**
     * 设置未认证 Authentication
     *
     * @param uuid  uuid
     * @param token 令牌
     */
    public JwtAuthenticationToken(Object uuid, Object token, Object loginUser) {
        super(null);
        this.uuid = uuid;
        this.token = token;
        this.loginUser = loginUser;
        setAuthenticated(false);
    }

    /**
     * 设置已认证 Authentication
     *
     * @param uuid        uuid
     * @param token       令牌
     * @param loginUser   登录信息信息
     * @param authorities 权限集合
     */
    public JwtAuthenticationToken(Object uuid, Object token, Object loginUser, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.uuid = uuid;
        this.token = token;
        this.loginUser = loginUser;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.loginUser;
    }

    @Override
    public Object getPrincipal() {
        return this.uuid;
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
        this.loginUser = null;
    }


}
