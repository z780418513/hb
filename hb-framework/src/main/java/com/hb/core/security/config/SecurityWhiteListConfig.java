package com.hb.core.security.config;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/10/25
 */
@Configuration
@ConfigurationProperties("hb.security")
@Data
public class SecurityWhiteListConfig {
    /**
     * 请求放行白名单
     */
    private List<String> whiteList = Lists.newArrayList("/user/login", "/user/register", "/user/register/mobile");
}
