package com.hb.framwork.security.filter;

import com.hb.framwork.security.token.LoginAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * https://blog.csdn.net/weixin_38326506/article/details/122751032
 */
public class LoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
    public static final String SPRING_SECURITY_FORM_MOBILECODE_KEY = "mobileCode";
    public static final String SPRING_SECURITY_FORM_LOGINTYPE_KEY = "logintype";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    private String mobilecodeParameter = SPRING_SECURITY_FORM_MOBILECODE_KEY;
    private String loginTypeParameter = SPRING_SECURITY_FORM_LOGINTYPE_KEY;
    private boolean postOnly = true;
    public LoginAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/my/login", "POST"));//LoginAuthenticationProcessingFilter过滤的路径为/my/login，其他路径不走此过滤器
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String loginType = request.getParameter(loginTypeParameter);
        String username = request.getParameter(usernameParameter);
        Authentication authRequest=null;
        if (loginType.equals("1")){//用户名密码登录
//            String username = request.getHeader(usernameParameter);
            String password = request.getParameter(passwordParameter);
            authRequest = new LoginAuthenticationToken(
                    username, password);
        }else if (loginType.equals("2")){//手机号短信登录
            String mobile = request.getParameter(mobileParameter);
            String mobilecode = request.getParameter(mobilecodeParameter);
            authRequest =new LoginAuthenticationToken(mobile, mobilecode);
        }
        return  this.getAuthenticationManager().authenticate(authRequest);
    }
}
