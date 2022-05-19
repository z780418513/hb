package com.hb.framwork.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 手机短信验证token
 * <p>
 * 基于UsernamePasswordAuthenticationToken 重写
 * 构造器中setAuthenticated(); 用于设置是否已认证,TRUE为已认证
 *
 * @Author hanbaolaoba
 * @Date 2022/05/19  08:32
 **/
public class MobileCodeAuthenticationToken extends AbstractAuthenticationToken {

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
     * @param phone   手机号
     * @param msgCode 手机短信
     */
    public MobileCodeAuthenticationToken(Object phone, Object msgCode) {
        super(null);
        this.principal = phone;
        this.credentials = msgCode;
        setAuthenticated(false);
    }

    /**
     * 设置已认证 Authentication
     *
     * @param phone       手机号
     * @param msgCode     手机短信
     * @param authorities 权限集合
     */
    public MobileCodeAuthenticationToken(Object phone, Object msgCode, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = phone;
        this.credentials = msgCode;
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
