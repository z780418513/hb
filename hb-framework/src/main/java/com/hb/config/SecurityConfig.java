package com.hb.config;

import com.hb.security.CustomizeAuthenticationEntryPoint;
import com.hb.security.CustomizeAuthenticationFailureHandler;
import com.hb.security.CustomizeAuthenticationSuccessHandler;
import com.hb.service.HbUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private HbUserDetailsService detailsService;

    // 登录成功处理逻辑
    @Resource
    private CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    // 匿名用户访问无权限资源时的异常
    @Resource
    private CustomizeAuthenticationEntryPoint authenticationEntryPoint;

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
     * 用户认证配置
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                // 认证失败处理器
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()

                .formLogin().loginPage("/login.html")
                .successHandler(authenticationSuccessHandler) // 登录成功处理拦截器
                .failureHandler(authenticationFailureHandler)
                .permitAll().and()

                // 过滤请求
                .authorizeRequests()
                .antMatchers("/login.html","/user/login").permitAll()  //登录请求放行
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
