package com.hb.framwork.security.service;

import com.hb.common.constants.SysConstant;
import com.hb.system.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Data
@Service
public class JwtTokenService {
    /**
     * 密钥KEY
     */
    @Value("${jwt.config.secret}")
    private String secret;

    /**
     * TokenKey
     */
    @Value("${jwt.config.token-header}")
    private String header;

    /**
     * 过期时间
     */
    @Value("${jwt.config.expiration}")
    private Integer expiration;


    /**
     * 生成token令牌
     *
     * @param user 用户
     * @return 令token牌
     */
    public String generateToken(LoginUser user) {
        String uuid = UUID.randomUUID().toString();

        return "token";
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(SysConstant.TOKEN_PREFIX)) {
            token = token.replace(SysConstant.TOKEN_PREFIX, "");
        }
        return token;
    }



}
