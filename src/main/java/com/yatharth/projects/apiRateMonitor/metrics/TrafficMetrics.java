package com.yatharth.projects.apiRateMonitor.metrics;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TrafficMetrics {

    public static final AtomicLong totalRequests = new AtomicLong();
    public static final AtomicLong allowedRequests = new AtomicLong();
    public static final AtomicLong rateLimitedRequests = new AtomicLong();
    public static final AtomicLong bannedRequests = new AtomicLong();

    public static final Map<String, AtomicLong> endpointCounts =
            new ConcurrentHashMap<>();
}
