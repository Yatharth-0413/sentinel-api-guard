package com.yatharth.projects.apiRateMonitor.services;


import com.yatharth.projects.apiRateMonitor.ratelimit.RateLimitFailureMode;
import com.yatharth.projects.apiRateMonitor.ratelimit.RateLimitKeyGenerator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RedisTokenBucketRateLimiter {

    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<List> tokenBucketScript;

    public RedisTokenBucketRateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;

        this.tokenBucketScript = new DefaultRedisScript<>();
        this.tokenBucketScript.setLocation(
                new ClassPathResource("token_bucket.lua")
        );
        this.tokenBucketScript.setResultType(List.class);
    }

//    public RateLimitResult allowRequest(
//            String redisKey,
//            int capacity,
//            double refillRate,
//            RateLimitFailureMode failureMode
//    ) {
//        long now = System.currentTimeMillis();
//
//        System.out.println("Using Redis key: [" + redisKey + "]");
//
//        List<Long> result = redisTemplate.execute(
//                tokenBucketScript,
//                Collections.singletonList(redisKey),
//                String.valueOf(capacity),
//                String.valueOf(refillRate),
//                String.valueOf(now)
//        );
//
//        boolean allowed = result.get(0) == 1;
//        int remaining = result.get(1).intValue();
//
//        return new RateLimitResult(allowed, remaining);
//    }


    public RateLimitResult allowRequest(
            String key,
            int capacity,
            double refillRate,
            RateLimitFailureMode failureMode
    ) {
        try {
            long now = System.currentTimeMillis();
            String redisKey = "rate_limit:" + key;

            List<Long> result = redisTemplate.execute(
                    tokenBucketScript,
                    Collections.singletonList(redisKey),
                    String.valueOf(capacity),
                    String.valueOf(refillRate),
                    String.valueOf(now)
            );

            boolean allowed = result.get(0) == 1;
            int remaining = result.get(1).intValue();

            return new RateLimitResult(allowed, remaining);

        } catch (Exception ex) {
            // 🔥 REDIS FAILURE HANDLING
            if (failureMode == RateLimitFailureMode.FAIL_OPEN) {
                return new RateLimitResult(true, capacity);
            } else {
                return new RateLimitResult(false, 0);
            }
        }
    }

}
