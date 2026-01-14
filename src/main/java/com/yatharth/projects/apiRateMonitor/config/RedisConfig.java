package com.yatharth.projects.apiRateMonitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

//@Configuration
//public class RedisConfig {
//
//    @Bean
//    public StringRedisTemplate redisTemplate() {
//        return new StringRedisTemplate(new LettuceConnectionFactory());
//    }
//}


@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Bean
    public StringRedisTemplate redisTemplate(
            LettuceConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }
}
