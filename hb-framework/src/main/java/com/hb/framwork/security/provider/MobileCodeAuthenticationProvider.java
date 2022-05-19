package com.hb.framwork.security.provider;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.hb.common.constants.SysConstant;
import com.hb.common.expection.BusinessException;
import com.hb.common.utils.RedisUtil;
import com.hb.framwork.security.service.UserDetailsServiceImpl;
import com.hb.framwork.security.token.MobileCodeAuthenticationToken;
import com.hb.system.mapper.SysUserMapper;
import com.hb.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 手机短信登入验证提供者
 *
 * @Author hanbaolaoba
 * @Date 2022/05/19  09:29
 **/
@Component
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisUtil redisUtil;


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
        // 验证手机短信
        String phone = (String) authentication.getPrincipal();
        String msgCode = (String) authentication.getCredentials();
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(msgCode)) {
            throw new BusinessException("认证失败:手机号或短信验证码为空");
        }
        String validCode = (String) redisUtil.get(SysConstant.PHONE_PREFIX + phone);
        redisUtil.del(SysConstant.PHONE_PREFIX + phone);
        if (StringUtils.isBlank(validCode)) {
            throw new BusinessException("认证失败:手机短信验证码过期或未发送");
        }
        if (!msgCode.equals(validCode)) {
            throw new BusinessException("认证失败:验证码不一致,请重新发送");
        }
        // 根据手机号查询用户信息
        SysUser user = sysUserMapper.findByPhone(phone);
        if (Objects.isNull(user)) {
            throw new BusinessException("认证失败:无该登录信息");
        }
        // 密码编码
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        // 授权
        user.setAuthorities(grantedAuthorities(user.getRoles()));
        MobileCodeAuthenticationToken authenticationToken = new MobileCodeAuthenticationToken(
                phone, msgCode, UserDetailsServiceImpl.grantedAuthorities(user.getRoles()));
        authenticationToken.setDetails(user);
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

    /**
     * 自定义实现权限转换
     *
     * @param roles 角色权限 "admin;guest"
     * @return List<GrantedAuthority>
     */
    public static List<GrantedAuthority> grantedAuthorities(String roles) {
        String[] roleList = roles.split(";");
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;

    }
}
