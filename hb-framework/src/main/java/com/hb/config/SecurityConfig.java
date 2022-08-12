package com.hb.config;

import com.hb.security.LoginAuthenticationProvider;
import com.hb.service.HbUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

/**
 * @author zhaochengshui
 */
@Configuration
public class SecurityConfig {

    @Resource
    private HbUserDetailsService detailsService;


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


    @Bean
    public LoginAuthenticationProvider loginAuthenticationProvider() {
        LoginAuthenticationProvider provider = new LoginAuthenticationProvider();
        provider.setUserDetailsService(detailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 关闭csrf
                .csrf().disable()
                // 认证失败处理器
                .formLogin().permitAll().and()
                // 过滤请求
                .authorizeRequests()
                // 登录请求放行 允许匿名访问
                .antMatchers("/user/login", "/captcha", "/login.html").anonymous()

                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated().and().build();


    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
