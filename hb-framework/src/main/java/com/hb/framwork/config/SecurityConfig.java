package com.hb.framwork.config;

import com.hb.framwork.security.CustomizeAuthenticationEntryPoint;
import com.hb.framwork.security.CustomizeAuthenticationFailureHandler;
import com.hb.framwork.security.CustomizeAuthenticationSuccessHandler;
import com.hb.framwork.security.LoginAuthenticationProvider;
import com.hb.framwork.security.service.HbUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author hanbaolaoba
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义UserDetailsService
     */
    @Resource
    private HbUserDetailsServiceImpl detailsService;

    /**
     * 登录成功拦截器
     */
    @Resource
    private CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 匿名用户访问无权限资源时的异常
     */
    @Resource
    private CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 认证失败拦截器
     */
    @Resource
    private CustomizeAuthenticationFailureHandler authenticationFailureHandler;


    /**
     * PasswordEncoder 是security的加密类,必须实现
     * BCryptPasswordEncoder 是一种强加密方式,security自带
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public LoginAuthenticationProvider loginAuthenticationProvider() {
        LoginAuthenticationProvider provider = new LoginAuthenticationProvider();
        provider.setUserDetailsService(detailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    /**
     * 用户认证配置
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                // 认证失败处理器
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()

                .formLogin().permitAll()
                // 登录成功处理拦截器
                .successHandler(authenticationSuccessHandler)
                // 登录失败处理拦截器
                .failureHandler(authenticationFailureHandler).and()

                // 过滤请求
                .authorizeRequests()
                // 登录请求放行 允许匿名访问
                .antMatchers("/user/login", "/captcha").anonymous()

                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 放行static下的css和img的静态资源
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/img/**");
    }
}
