package com.hb.service;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hb.common.constants.SecurityConstants;
import com.hb.common.enums.BusinessExceptionEnum;
import com.hb.common.enums.LoginTypeEnum;
import com.hb.common.exceptions.BusinessException;
import com.hb.common.utils.IdUtils;
import com.hb.common.utils.IpUtils;
import com.hb.common.utils.JwtUtils;
import com.hb.common.utils.RedisUtils;
import com.hb.system.model.LoginUser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * tokenService 服务类
 *
 * @author zhaochengshui
 */
@Service
@Data
@Slf4j
public class TokenService {

    /**
     * 令牌头部(默认'')
     */
    @Value("${jwt.token-header:}")
    private String tokenHeader;

    /**
     * 认证令牌过期时间
     */
    @Value("${jwt.expiration}")
    private long expiration;

    @Resource
    private RedisUtils redisUtil;
    @Resource
    private JwtUtils jwtUtil;


    /**
     * 刷新token
     *
     * @param token 认证令牌
     * @return Token
     */
    public String refreshToken(String token, HttpServletRequest request) {
        // 校验token
        if (!validateToken(token)) {
            throw new BusinessException(BusinessExceptionEnum.TOKEN_VALID_FAIL);
        }
        // 获得loginUser
        String uuid = jwtUtil.getClaim(token).get("UUID").toString();
        Object loginUser = redisUtil.get(SecurityConstants.TOKEN_REDIS_PREFIX + uuid);
        if (Objects.isNull(loginUser)) {
            throw new BusinessException(BusinessExceptionEnum.TOKEN_IS_EXPIRE);
        }
        return getOathToken((LoginUser) loginUser, request);
    }

    /**
     * 生成OathToken
     * 使用UUID为载体生成token，用户信息存放到redis中去
     *
     * @param user 登录用户信息
     * @return Token
     */
    public String getOathToken(LoginUser user, HttpServletRequest request) {
        setUser(user, request);
        String token = this.tokenHeader + jwtUtil.generateAccessToken(convertLoginUser(user));
        // 放到redis中，key(login-token:username)  value(loginUser)
        redisUtil.set(SecurityConstants.TOKEN_REDIS_PREFIX + user.getUsername(), user, expiration * 60 * 60);
        return token;
    }

    /**
     * 校验token
     *
     * @param token 令牌
     * @return boolean
     */
    public boolean validateToken(String token) {
        if (StringUtils.isBlank(token)) {
            log.debug("token为空");
            return false;
        }
        if (!token.startsWith(tokenHeader)) {
            log.debug("非法token:[ {} ]", token);
            return false;
        }
        return jwtUtil.verifierToken(token.substring(tokenHeader.length()));
    }


    public Map<String, Claim> loginUserByToken(String token) {
        if (StringUtils.isNotBlank(token) || token.startsWith(tokenHeader)) {
            return jwtUtil.getClaim(token.substring(tokenHeader.length()));
        }
        return null;
    }


    private HashMap<String, Object> convertLoginUser(LoginUser user) {
        HashMap<String, Object> map = new HashMap<>(12);
        map.put("ip", user.getIp());
        map.put("username", user.getUsername());
        return map;
    }


    public void setUser(LoginUser loginUser, HttpServletRequest request) {
        loginUser.setLoginType(LoginTypeEnum.USERNAME_PASSWORD.getCode());
        loginUser.setUuid(IdUtils.simpleUUID());
        loginUser.setIp(IpUtils.getIpAddr(request));
        loginUser.setLoginTime(System.currentTimeMillis());
    }


    public String getUserNameFromToken(String token) {
        if (StringUtils.isNotBlank(token) || token.startsWith(tokenHeader)) {
            Map<String, Claim> claims = jwtUtil.getClaim(token.substring(tokenHeader.length()));
            return claims.get("username").asString();
        }
        return "";
    }




}
