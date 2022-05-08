package com.zhao.hb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhao.hb.mapper")
public class HbApplication {

    public static void main(String[] args) {
        SpringApplication.run(HbApplication.class, args);
    }

}
