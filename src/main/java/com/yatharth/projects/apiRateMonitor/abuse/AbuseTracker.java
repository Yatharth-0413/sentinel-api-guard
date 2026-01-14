package com.yatharth.projects.apiRateMonitor.abuse;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AbuseTracker {

    private final StringRedisTemplate redisTemplate;

    public AbuseTracker(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long recordRequest(String key) {
        try{

        String redisKey = "abuse:count:" + key;

        Long count = redisTemplate.opsForValue().increment(redisKey);

        // short window (10 seconds)
        redisTemplate.expire(redisKey, Duration.ofSeconds(10));

        return count != null ? count : 0;
        } catch (Exception ex){
            return 0;
        }
    }
}
