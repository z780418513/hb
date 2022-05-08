package com.zhao.hb.config;

import com.zhao.hb.service.HbUserDetailsService;
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
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/user/login") // 登录接口post
                .defaultSuccessUrl("/index.html") // 认证成功跳转地址
//                .failureUrl("/error.html") // 认证失败跳转地址
                .permitAll()


                .and().authorizeRequests()
                .antMatchers("/user/login").permitAll()  //登录请求放行
//                .antMatchers("/error.html").permitAll()  //登入失败放行
                .anyRequest().authenticated() // 所有的请求都需要被认证

                .and().csrf().disable(); //关闭csrf

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 放行static下的css和img的静态资源
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/img/**");
    }
}
