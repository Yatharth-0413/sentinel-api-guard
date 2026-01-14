package com.yatharth.projects.apiRateMonitor.ratelimit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimitPolicyService {

    @Autowired
    private RateLimitPolicyStore store;

    public RateLimitPolicy resolve(
            RateLimitDimension dimension,
            String endpoint,
            UserTier tier
    ) {
        if (dimension == RateLimitDimension.IP_ENDPOINT) {
            RateLimitPolicy p =
                    store.getPolicy("IP_ENDPOINT:" + endpoint);
            return p != null ? p : defaultPolicy();
        }

        if (dimension == RateLimitDimension.USER_ENDPOINT) {
            RateLimitPolicy p =
                    store.getPolicy("USER_ENDPOINT:" + tier.name());
            return p != null ? p : defaultPolicy();
        }

        return defaultPolicy();
    }

    private RateLimitPolicy defaultPolicy() {
        return new RateLimitPolicy(5, 5.0 / 60000);
    }
}
