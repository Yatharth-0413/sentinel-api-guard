package com.yatharth.projects.apiRateMonitor.services;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryRateLimiter {

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_SIZE_MS = 60_000; // 1 minute

    private final Map<String, RequestCounter> store = new ConcurrentHashMap<>();

    public RateLimitResult allowRequest(String key) {
        long now = System.currentTimeMillis();

        store.putIfAbsent(key, new RequestCounter(0, now));
        RequestCounter counter = store.get(key);

        synchronized (counter) {
            if (now - counter.windowStart > WINDOW_SIZE_MS) {
                counter.count = 1;
                counter.windowStart = now;
                return new RateLimitResult(true, MAX_REQUESTS - 1);
            }

            if (counter.count < MAX_REQUESTS) {
                counter.count++;
                return new RateLimitResult(true, MAX_REQUESTS - counter.count);
            }

            return new RateLimitResult(false, 0);
        }
    }


    static class RequestCounter {
        int count;
        long windowStart;

        RequestCounter(int count, long windowStart) {
            this.count = count;
            this.windowStart = windowStart;
        }
    }
}
