package com.hb.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hb.system.entity.SysRole;
import com.hb.system.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomUserDetails extends SysUser implements UserDetails {
    /**
     * 角色集合
     */
    @TableField(exist = false)
    private List<SysRole> roles;


    /**
     * 用户是启用还是禁用
     *
     * @return this.enable
     */
    @Override
    public boolean isEnabled() {
        return this.isEnable();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(sysRole -> new SimpleGrantedAuthority(sysRole.getRoleName()))
                .collect(Collectors.toList());
    }

    /**
     * 账号是否过期
     *
     * @return 默认true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否不被锁定
     *
     * @return 默认true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户的凭据（密码）是否未过期
     *
     * @return 默认true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
