package com.hb.configurion;

import com.hb.common.utils.JWTUtils;
import com.hb.core.utils.RedisUtils;
import com.hb.common.properties.JWTProperties;
import com.hb.core.security.service.JWTTokenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true")
@EnableConfigurationProperties(JWTProperties.class)
public class JWTAutoConfiguration {

    @Bean
    @ConditionalOnBean(RedisUtils.class)
    public JWTTokenService jwtTokenService(JWTProperties properties, RedisUtils redisUtil) {
        JWTTokenService jwtTokenService = new JWTTokenService(properties.getTokenHeader(), properties.getExpiration());
        JWTUtils jwtUtils = new JWTUtils(properties.getSecret(), properties.getExpiration(), properties.getRefreshExpiration());
        jwtTokenService.setJwtUtil(jwtUtils);
        jwtTokenService.setRedisUtil(redisUtil);
        return jwtTokenService;
    }
}
