package com.atguigu.locks.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class AppJedisConfig {


    /**
     * 1、
     * @param properties
     * @return
     * @throws Exception
     */
    @Bean
    public JedisPool jedisPoolConfig(RedisProperties properties) throws Exception {
        //1、连接工厂中所有信息都有。
        JedisPoolConfig config = new JedisPoolConfig();

        RedisProperties.Pool pool = properties.getJedis().getPool();


        //这些配置
        config.setMaxIdle(pool.getMaxIdle());
        config.setMaxTotal(pool.getMaxActive());

        JedisPool jedisPool = null;
        jedisPool = new JedisPool(config, properties.getHost(), properties.getPort());
        return jedisPool;
    }









}
