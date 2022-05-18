package com.hb.framwork.security.provider;

import com.hb.framwork.security.token.LoginAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginAuthenticationProvider implements AuthenticationProvider {
    protected final Log logger = LogFactory.getLog(getClass());
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IUserService userServiceImpl;

    private PasswordEncoder passwordEncoder;

    public LoginAuthenticationProvider(){
        setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }


    /**
     * 进行校验
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginAuthenticationToken loginToken = (LoginAuthenticationToken)authentication;
        String loginType = loginToken.getLoginType();
        Users users = null;
        try {
            if ("2".equals(loginType)){//手机号登录
                String mobile = loginToken.getName();
                users = userServiceImpl.findUserByMobile(mobile);

            }else if ("1".equals(loginType)){//用户名密码登录
                String username = loginToken.getName();
                users = userServiceImpl.findUserByName(username);
            }
        } catch (UsernameNotFoundException notFound) {//查询失败抛出未找到用户异常
            logger.debug("User '" + loginToken.getName() + "' not found");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
        if (users == null){//未找到用户抛出未找到用户异常
            logger.debug("User '" + loginToken.getName() + "' not found");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
        //验证
        try {
            if ("2".equals(loginType)){//手机号登录
                additionalAuthenticationChecks2(users.getPassword(),authentication.getCredentials().toString());
            }else if ("1".equals(loginType)){//用户名密码登录
                additionalAuthenticationChecks1(users.getPassword(),authentication.getCredentials().toString());
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw e;
        }
        Object principalToReturn = users;
        UserDetails user = new User(users.getUsername(),users.getPassword(),null);
        return createSuccessAuthentication(principalToReturn, authentication, user);
    }

    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication, UserDetails user) {
        LoginAuthenticationToken result = new LoginAuthenticationToken(
                principal, authentication.getCredentials(),
                authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());

        return result;
    }

    /**
     * 用户密码校验
     * @param pasword 数据库中查询到的密码
     * @param presentedPassword 前台传递的密码
     * @throws AuthenticationException
     */
    protected void additionalAuthenticationChecks1(String pasword,String presentedPassword)
            throws AuthenticationException {
        if (StringUtils.isEmpty(presentedPassword)) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        if (!passwordEncoder.matches(presentedPassword, pasword)) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

    /**
     * 用户密码校验
     * @param mobile 手机号
     * @param code 手机号验证码
     * @throws AuthenticationException
     */
    protected void additionalAuthenticationChecks2(String mobile,String code)
            throws AuthenticationException {
        if (StringUtils.isEmpty(code)) {
            logger.debug("Authentication failed: no code");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
        //校验手机号验证码是否超过了有效期

        //校验手机号验证码是否正确

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (LoginAuthenticationToken.class
                .isAssignableFrom(authentication));//注意此处使用的是LoginAuthenticationToken
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
