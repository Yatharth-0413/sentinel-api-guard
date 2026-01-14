package com.yatharth.projects.apiRateMonitor.admin;

import com.yatharth.projects.apiRateMonitor.ratelimit.RateLimitPolicy;
import com.yatharth.projects.apiRateMonitor.ratelimit.RateLimitPolicyStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/rate-limits")
public class RateLimitAdminController {

    @Autowired
    private RateLimitPolicyStore store;

    @GetMapping
    public Map<String, RateLimitPolicy> listPolicies() {
        return store.getAllPolicies();
    }

    @PostMapping
    public void updatePolicy(@RequestBody RateLimitUpdateRequestDto request) {
        store.updatePolicy(request.getKey(), request.getPolicy());
    }

}
