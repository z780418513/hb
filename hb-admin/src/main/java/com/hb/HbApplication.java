package com.hb;

import com.hb.framwork.config.JWTConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author hanbaolaoba
 */
@SpringBootApplication
@MapperScan("com.hb.system.mapper")
@EnableConfigurationProperties(JWTConfig.class)
public class HbApplication {

    public static void main(String[] args) {
        SpringApplication.run(HbApplication.class, args);
    }

}
