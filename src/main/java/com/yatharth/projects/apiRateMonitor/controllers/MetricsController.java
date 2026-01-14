package com.yatharth.projects.apiRateMonitor.controllers;

import com.yatharth.projects.apiRateMonitor.metrics.TimeSeriesMetrics;
import com.yatharth.projects.apiRateMonitor.metrics.TrafficMetrics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MetricsController {

    @GetMapping("/metrics")
    public Map<String, Object> getMetrics() {
        return Map.of(
                "totalRequests", TrafficMetrics.totalRequests.get(),
                "allowedRequests", TrafficMetrics.allowedRequests.get(),
                "rateLimitedRequests", TrafficMetrics.rateLimitedRequests.get(),
                "bannedRequests", TrafficMetrics.bannedRequests.get(),
                "endpointCounts",
                TrafficMetrics.endpointCounts.entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().get()
                        ))
        );
    }

    @GetMapping("/metrics/timeseries")
    public Map<String, Object> getTimeSeries() {
        return Map.of(
                "requests", TimeSeriesMetrics.requestsPerSecond,
                "rateLimited", TimeSeriesMetrics.rateLimitedPerSecond,
                "banned", TimeSeriesMetrics.bannedPerSecond
        );
    }

}
