package com.yatharth.projects.apiRateMonitor.metrics;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TimeSeriesMetrics {

    // epochSecond -> count
    public static final Map<Long, AtomicLong> requestsPerSecond =
            new ConcurrentHashMap<>();

    public static final Map<Long, AtomicLong> rateLimitedPerSecond =
            new ConcurrentHashMap<>();

    public static final Map<Long, AtomicLong> bannedPerSecond =
            new ConcurrentHashMap<>();

    public static void incrementRequest() {
        long second = Instant.now().getEpochSecond();
        requestsPerSecond
                .computeIfAbsent(second, k -> new AtomicLong())
                .incrementAndGet();
    }

    public static void incrementRateLimited() {
        long second = Instant.now().getEpochSecond();
        rateLimitedPerSecond
                .computeIfAbsent(second, k -> new AtomicLong())
                .incrementAndGet();
    }

    public static void incrementBanned() {
        long second = Instant.now().getEpochSecond();
        bannedPerSecond
                .computeIfAbsent(second, k -> new AtomicLong())
                .incrementAndGet();
    }
}
