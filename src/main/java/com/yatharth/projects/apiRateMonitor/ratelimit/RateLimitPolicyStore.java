package com.yatharth.projects.apiRateMonitor.ratelimit;


import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitPolicyStore {

    private final Map<String, RateLimitPolicy> policies =
            new ConcurrentHashMap<>();

    public RateLimitPolicyStore() {
        // DEFAULT POLICIES (bootstrapped)
        policies.put("IP_ENDPOINT:/test", new RateLimitPolicy(5, 5.0 / 60000));
        policies.put("IP_ENDPOINT:/login", new RateLimitPolicy(10, 10.0 / 60000));

        policies.put("USER_ENDPOINT:FREE", new RateLimitPolicy(5, 5.0 / 60000));
        policies.put("USER_ENDPOINT:PREMIUM", new RateLimitPolicy(50, 50.0 / 60000));
    }

    public RateLimitPolicy getPolicy(String key) {
        return policies.get(key);
    }

    public void updatePolicy(String key, RateLimitPolicy policy) {
        policies.put(key, policy);
    }

    public Map<String, RateLimitPolicy> getAllPolicies() {
        return policies;
    }
}
