package com.hb.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaochengshui
 * @description 默认用户配置类
 * @date 2022/10/25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "hb.user")
public class DefaultUserConfig {
    /**
     * 默认昵称前缀
     */
    private String defaultNickName = "用户";

    /**
     * 默认头像
     */
    private String defaultAvatar;

    /**
     * 默认密码
     */
    private String defaultPassword = "12345678";
}
