package com.yatharth.projects.apiRateMonitor.abuse;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class BanService {

    private final StringRedisTemplate redisTemplate;

    public BanService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void ban(String key) {
        try {
            redisTemplate.opsForValue()
                    .set("ban:" + key, "1", Duration.ofMinutes(1));
        } catch (Exception ex) {
            // Redis down → cannot persist ban, ignore
        }
    }


    public boolean isBanned(String key) {
        try {
            return Boolean.TRUE.equals(
                    redisTemplate.hasKey("ban:" + key)
            );
        } catch (Exception ex) {
            // 🔥 Redis is down — FAIL OPEN for bans
            return false;
        }
    }

}
