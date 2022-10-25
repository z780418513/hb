package com.hb.core.security.config;

import com.hb.core.security.access.CustomAccessDecisionManager;
import com.hb.core.security.access.CustomFilterInvocationSecurityMetadataSource;
import com.hb.core.security.filter.TokenAuthenticationFilter;
import com.hb.core.security.handler.CustomAccessDeniedHandler;
import com.hb.core.security.handler.CustomAuthenticationEntryPoint;
import com.hb.core.security.porvider.TokenAuthenticationProvider;
import com.hb.core.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * springSecurity执行流程
 * 用户请求==> 认证 ==> 授权
 *
 * @author zhaochengshui
 * @description security配置类
 * @date 2022-08-17
 */
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

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


    //------------------------Provider------------------------
    /**
     * 令牌认证提供者
     */
    @Resource
    private TokenAuthenticationProvider tokenAuthenticationProvider;

    /**
     * 用户名密码认证管理提供者
     *
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * 认证管理器
     *
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(daoAuthenticationProvider(), tokenAuthenticationProvider);
    }

    //------------------------Filter------------------------

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    /**
     * 授权管理器
     */
    @Resource
    private CustomAccessDecisionManager accessDecisionManager;


    @Resource
    private CustomFilterInvocationSecurityMetadataSource securityMetadataSource;


    //------------------------Handler------------------------

    @Resource
    private CustomAccessDeniedHandler accessDeniedHandler;
    @Resource
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * http配置
     *
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 关闭csrf
                .csrf().disable()
                // 永远不会创建HttpSession并且永远不会使用它来获取SecurityContext,适用于前后端分离
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 过滤请求
                .authorizeRequests()
//                /* anonymous 匿名访问（登录后不能访问，未登录可以访问）
//                 * permitAll 放行所有（不管有无登录，可以访问）
//                 * */
//                .antMatchers("/user/login").anonymous()
//                .antMatchers("/captcha", "/user/register", "/user/register/mobile").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                // 配置accessDecisionManager和securityMetadataSource
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setAccessDecisionManager(accessDecisionManager);
                        fsi.setSecurityMetadataSource(securityMetadataSource);
                        return fsi;
                    }
                }).and()
                // 自定义过滤器
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 授权失败拦截器
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint).and()
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().antMatchers("/test", "/ignore2");
        };
    }


}
