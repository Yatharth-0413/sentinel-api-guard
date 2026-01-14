package com.yatharth.projects.apiRateMonitor.ratelimit;

import org.springframework.stereotype.Component;

@Component
public class TierPolicyResolver {

    public RateLimitPolicy resolve(UserTier tier) {
        return switch (tier) {
            case FREE -> new RateLimitPolicy(5, 5.0 / 60000);
            case PREMIUM -> new RateLimitPolicy(20, 20.0 / 60000);
        };
    }
}
