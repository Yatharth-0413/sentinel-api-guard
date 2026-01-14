package com.yatharth.projects.apiRateMonitor.config;

import com.yatharth.projects.apiRateMonitor.ratelimit.RateLimitFailureMode;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class RateLimitConfig {

    private final AtomicReference<Boolean> enabled =
            new AtomicReference<>(true);

    private final AtomicReference<RateLimitFailureMode> failureMode =
            new AtomicReference<>(RateLimitFailureMode.FAIL_OPEN);

    public boolean isEnabled() {
        return enabled.get();
    }

    public void setEnabled(boolean value) {
        enabled.set(value);
    }

    public RateLimitFailureMode getFailureMode() {
        return failureMode.get();
    }

    public void setFailureMode(RateLimitFailureMode mode) {
        failureMode.set(mode);
    }
}