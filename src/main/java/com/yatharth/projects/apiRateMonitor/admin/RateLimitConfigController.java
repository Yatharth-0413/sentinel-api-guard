package com.yatharth.projects.apiRateMonitor.admin;


import com.yatharth.projects.apiRateMonitor.config.RateLimitConfig;
import com.yatharth.projects.apiRateMonitor.ratelimit.RateLimitFailureMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/config")
public class RateLimitConfigController {

    @Autowired
    private RateLimitConfig config;

    @GetMapping
    public Map<String, Object> getConfig() {
        return Map.of(
                "enabled", config.isEnabled(),
                "failureMode", config.getFailureMode()
        );
    }

    @PostMapping("/enabled")
    public void setEnabled(@RequestParam boolean enabled) {
        config.setEnabled(enabled);
    }

    @PostMapping("/failure-mode")
    public void setFailureMode(@RequestParam RateLimitFailureMode mode) {
        config.setFailureMode(mode);
    }
}
