package com.hb.framwork.config;

import com.hb.framwork.security.*;
import com.hb.framwork.security.service.HbUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author hanbaolaoba
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 登录成功处理类
     */
    @Resource
    private UserLoginSuccessHandler authenticationSuccessHandler;

    /**
     * 登录失败处理类
     */
    @Resource
    private UserLoginFailureHandler authenticationFailureHandler;

    /**
     * 自定义注销成功处理器
     */
    @Resource
    private UserLogoutSuccessHandler userLogoutSuccessHandler;

    /**
     * 自定义暂无权限处理器
     */
    @Resource
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;

    /**
     * 用户未登录处理类
     */
    @Resource
    private UserAuthenticationEntryPointHandler authenticationEntryPoint;

    /**
     * 自定义登录逻辑验证器
     */
    @Resource
    private UserAuthenticationProvider userAuthenticationProvider;


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
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
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


    /**
     * 配置登录验证逻辑
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 用户未登录处理类
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint).and()

                // 配置登录地址
                .formLogin().loginProcessingUrl("/user/login")
                // 登录成功处理拦截器
                .successHandler(authenticationSuccessHandler)
                // 登录失败处理拦截器
                .failureHandler(authenticationFailureHandler).and()

                // 配置退出登录
                .logout().logoutUrl("/user/logout")
                .logoutSuccessHandler(userLogoutSuccessHandler).and()

                // 配置没有权限自定义处理类
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler).and()


                // 过滤请求
                .authorizeRequests()
                // 登录请求放行 允许匿名访问
                .antMatchers("/user/login", "/captcha").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated().and()

                // 开启跨域
                .cors().and()
                //关闭csrf
                .csrf().disable()
                // 永远不会创建HttpSession并且永远不会使用它来获取SecurityContext,适用于前后端分离
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 禁用缓存
                .headers().cacheControl();

        http            // 自定义jwt过滤器
                .addFilter(new JwtAuthenticationTokenFilter(authenticationManagerBean()));


    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // 放行static下的css和img的静态资源
//        web.ignoring()
//                .antMatchers("/css/**")
//                .antMatchers("/img/**");
//    }
}
