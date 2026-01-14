package com.yatharth.projects.apiRateMonitor.ratelimit;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RateLimitPolicyRegistry {

    private final Map<String, RateLimitPolicy> endpointPolicies = Map.of(
            "/login", new RateLimitPolicy(3, 3.0 / 60000),
            "/test", new RateLimitPolicy(5, 5.0 / 60000)
    );

    public RateLimitPolicy resolve(String endpoint) {
        return endpointPolicies.getOrDefault(
                endpoint,
                new RateLimitPolicy(10, 10.0 / 60000)
        );
    }
}

