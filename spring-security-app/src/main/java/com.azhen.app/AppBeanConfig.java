package com.azhen.app;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AppBeanConfig {
    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate restTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate =  new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
