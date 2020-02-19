package com.atguigu.gmall.portal.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "gmall.pool")
public class PoolProperties {

    private Integer coreSize;
    private Integer maximumPoolSize;
    private Integer queueSize;


}
