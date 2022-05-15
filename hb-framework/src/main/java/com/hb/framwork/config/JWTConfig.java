package com.hb.framwork.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 */
@Data
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt.config")
public class JWTConfig {
    /**
     * 密钥KEY
     */
    private String secret;
    /**
     * TokenKey
     */
    private String tokenHeader;
    /**
     * Token前缀字符
     */
    private String tokenPrefix;
    /**
     * 过期时间
     */
    private Integer expiration;
    /**
     * 不需要认证的接口
     */
    private String antMatchers;

}
