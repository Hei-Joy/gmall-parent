package com.atguigu.locks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;


/**
 * 以后Jedis的自动【配置
 *
 * 1）、application.properties配置所有redis相关信息
 * 2）、
 */
@SpringBootApplication
public class LocksTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocksTestApplication.class, args);
    }

}
