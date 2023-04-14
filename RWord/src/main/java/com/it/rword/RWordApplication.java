package com.it.rword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
// 开启缓存功能
@EnableCaching
public class RWordApplication {

    public static void main(String[] args) {
        SpringApplication.run(RWordApplication.class, args);
    }

}
