package com.hb.common.constants;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/16
 */
public class SecurityConstants {
    /**
     * 认证令牌 的redis前缀
     */
    public static final String TOKEN_REDIS_PREFIX = "login-token:";

    /**
     * 认证令牌 请求头标识
     */
    public static final String TOKEN_HEADER_FLAG = "Authorization";

    /**
     * 刷新令牌 请求头标识
     */
    public static final String REFRESH_TOKEN_HEADER_FLAG = "RefreshToken";

    /**
     * 令牌 前缀
     */
    public static final String TOKEN_PREFIX_FLAG = "Bearer ";

    /**
     * 验证码redis前缀
     */
    public static final String CAPTCHA_PREFIX = "captcha:";

    /**
     * 认证令牌 请求头标识
     */
    public static final String USER_KEY_HEADER_FLAG = "userKey";

    /**
     * 资源授权 redis前缀
     */
    public static final String RESOURCE_AUTH_REDIS_PREFIX = "resource_auth:";

    /**
     * 登录接口url
     */
    public static final String LOGIN_URL = "/user/login";




}
