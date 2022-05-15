package com.hb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author hanbaolaoba
 */
@SpringBootApplication
@MapperScan("com.hb.system.mapper")
public class HbApplication {

    public static void main(String[] args) {
        SpringApplication.run(HbApplication.class, args);
    }

}
