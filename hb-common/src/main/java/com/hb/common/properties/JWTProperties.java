package com.hb.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {

    /**
     * 是否开启
     */
    private boolean enable;

    /**
     * 令牌头部(默认'')
     */
    private String tokenHeader;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 认证令牌过期时间
     */
    private long expiration;

    /**
     * 刷新令牌过期时间(默认7天)
     */
    private long refreshExpiration;
}
