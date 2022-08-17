package com.hb.common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JwtUtil工具类
 *
 * @author zhaochengshui
 */
@Component
public class JwtUtil {
    public final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 密钥
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * 认证令牌过期时间
     */
    @Value("${jwt.expiration}")
    private long expiration;
    /**
     * 刷新令牌过期时间(默认7天)
     */
    @Value("${jwt.refresh-expiration:168}")
    private long refreshExpiration;

    /**
     * 生成令牌
     *
     * @param claims     数据声明
     * @param expiration 过期时间
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims, long expiration) {
        try {
            // 设置过期时间
            Date issuedTime = new Date();
            Date expireTime = new Date(System.currentTimeMillis() + expiration * 1000 * 60 * 60);
            //私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            // 返回token字符串
            return JWT.create()
                    // 载体
                    .withPayload(claims)
                    // 发行时间
                    .withIssuedAt(issuedTime)
                    // 过期时间
                    .withExpiresAt(expireTime)
                    // 加密算法
                    .sign(algorithm);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 生成认证令牌
     *
     * @param claims 数据声明
     * @return 认证令牌
     */
    public String generateAccessToken(Map<String, Object> claims) {
        return createToken(claims, this.expiration);
    }

    /**
     * 生成刷新令牌
     *
     * @param claims 数据声明
     * @return 刷新令牌
     */
    public String generateRefreshToken(Map<String, Object> claims) {
        return createToken(claims, this.refreshExpiration);
    }


    /**
     * 验证token是否有效
     *
     * @param token 令牌
     * @return 是否有效
     */
    public Boolean verifierToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(this.secret)).build();
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            log.warn("token验证失败:[ {} ]", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @param token 令牌
     * @return 载体信息
     */
    public Map<String, Claim> getClaim(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaims();
        } catch (JWTDecodeException e) {
            log.warn("token解码失败:[ {} ]", e.getMessage());
            return null;
        }
    }

    /**
     * 获得token中的过期时间
     *
     * @param token 令牌
     * @return 过期时间时间戳
     */
    public Long getExpiration(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt().getTime();
        } catch (JWTDecodeException e) {
            log.warn("token解码失败:[ {} ]", e.getMessage());
            return null;
        }
    }

}
