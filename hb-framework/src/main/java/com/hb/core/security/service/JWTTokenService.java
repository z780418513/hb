package com.hb.core.security.service;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hb.common.constants.SecurityConstants;
import com.hb.common.enums.LoginTypeEnum;
import com.hb.common.enums.SysExceptionEnum;
import com.hb.common.exceptions.SysException;
import com.hb.common.utils.IdUtils;
import com.hb.common.utils.IpUtils;
import com.hb.common.utils.JWTUtils;
import com.hb.core.utils.RedisUtils;
import com.hb.system.entity.SysUser;
import com.hb.system.model.CustomUserDetails;
import com.hb.system.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * tokenService 服务类
 *
 * @author zhaochengshui
 */

public class JWTTokenService {
    public final Logger log = LoggerFactory.getLogger(JWTTokenService.class);

    /**
     * 令牌头部(默认'')
     */
    private final String tokenHeader;
    /**
     * 认证令牌过期时间
     */
    private final long expiration;

    private RedisUtils redisUtil;

    private JWTUtils jwtUtil;

    public JWTTokenService(String tokenHeader, long expiration) {
        this.tokenHeader = tokenHeader;
        this.expiration = expiration;
    }

    public void setRedisUtil(RedisUtils redisUtil) {
        this.redisUtil = redisUtil;
    }

    public void setJwtUtil(JWTUtils jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 刷新token
     *
     * @param token 认证令牌
     * @return Token
     */
    public String refreshToken(String token, HttpServletRequest request) {
        // 校验token
        if (!validateToken(token)) {
            throw new SysException(SysExceptionEnum.TOKEN_VALID_FAIL);
        }
        // 获得loginUser
        String username = getUserNameFromToken(token);
        Object loginUser = redisUtil.get(SecurityConstants.TOKEN_REDIS_PREFIX + username);
        if (Objects.isNull(loginUser)) {
            throw new SysException(SysExceptionEnum.TOKEN_IS_EXPIRE);
        }
        return getOathToken((SysUser) loginUser, request);
    }

    /**
     * 生成OathToken
     * 使用UUID为载体生成token，用户信息存放到redis中去
     *
     * @param user 登录用户信息
     * @return Token
     */
    public String getOathToken(SysUser user, HttpServletRequest request) {
        LoginUser loginUser = setUser(user, request);
        String token = this.tokenHeader + jwtUtil.generateAccessToken(convertLoginUser(loginUser));
        // 放到redis中，key(login-token:username)  value(loginUser)
        redisUtil.set(SecurityConstants.TOKEN_REDIS_PREFIX + user.getUsername(), loginUser, expiration * 60 * 60);
        return token;
    }

    /**
     * 校验token
     *
     * @param token 令牌
     * @return boolean
     */
    public boolean validateToken(String token) {
        checkTokenHeader(token);
        return jwtUtil.verifierToken(token.substring(tokenHeader.length()));
    }


    /**
     * token 存放的数据
     *
     * @param user
     * @return
     */
    private HashMap<String, Object> convertLoginUser(LoginUser user) {
        HashMap<String, Object> map = new HashMap<>(12);
        map.put("ip", user.getIp());
        map.put("username", user.getUsername());
        map.put("id", user.getId());
        return map;
    }


    public LoginUser setUser(SysUser user, HttpServletRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) user;
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(userDetails, loginUser);
        loginUser.setLoginType(LoginTypeEnum.USERNAME_PASSWORD.getCode());
        loginUser.setUuid(IdUtils.simpleUUID());
        loginUser.setIp(IpUtils.getIpAddr(request));
        loginUser.setLoginTime(System.currentTimeMillis());
        return loginUser;
    }


    /**
     * 获得token中的username
     *
     * @param token 令牌
     * @return username
     */
    public String getUserNameFromToken(String token) {
        checkTokenHeader(token);
        Map<String, Claim> claims = jwtUtil.getClaim(token.substring(tokenHeader.length()));
        return claims.get("username").asString();
    }


    /**
     * 校验token头
     *
     * @param token 令牌
     */
    public void checkTokenHeader(String token) {
        if (StringUtils.isBlank(token) || !token.startsWith(tokenHeader)) {
            throw new SysException(SysExceptionEnum.TOKEN_ILLEGALITY);
        }
    }

    /**
     * 获得过期时间戳
     *
     * @param token 令牌
     * @return 过期时间戳
     */
    public Long getExpirationDateTime(String token) {
        checkTokenHeader(token);
        return jwtUtil.getExpiration(token.substring(tokenHeader.length()));
    }


    public Object getLoginUserFromRedis(String token) {
        String username = getUserNameFromToken(token);
        return redisUtil.get(SecurityConstants.TOKEN_REDIS_PREFIX + username);
    }


}
