package com.hb.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hb.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author zhaochengshui
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class LoginUser extends BaseEntity implements UserDetails {
    @TableId(type = IdType.ASSIGN_UUID)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 角色
     */
    @TableField(exist = false)
    private String roles;

    /**
     * 启用状态
     */
    @TableField("is_enable")
    private boolean enable;

    /**
     * 权限集合
     */
    @TableField(exist = false)
    private List<GrantedAuthority> authorities;

    /**
     * uuid
     */
    @TableField(exist = false)
    private String uuid;

    /**
     * 登录时间(时间戳)
     */
    @TableField(exist = false)
    private Long loginTime;

    /**
     * 过期时间
     */
    @TableField(exist = false)
    private Long expireTime;

    /**
     * ip地址
     */
    @TableField(exist = false)
    private String ip;

    /**
     * 登录方式
     */
    @TableField(exist = false)
    private Integer loginType;

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
     *
     * @return this.enable
     */
    @Override
    public boolean isEnabled() {
        return this.enable;
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
