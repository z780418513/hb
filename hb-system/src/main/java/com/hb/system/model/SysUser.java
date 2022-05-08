package com.hb.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@TableName("sys_user")
public class SysUser implements UserDetails {
    @TableId(type = IdType.ASSIGN_UUID)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    private String roles;

    private boolean enable;

    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 用户是启用还是禁用
     * @return this.enable
     */
    @Override
    public boolean isEnabled() {
        return this.enable;
    }

    /**
     * 账号是否过期
     * @return 默认true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否不被锁定
     * @return 默认true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户的凭据（密码）是否未过期
     * @return 默认true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
