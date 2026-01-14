//package com.yatharth.projects.apiRateMonitor.admin;
//import com.yatharth.projects.apiRateMonitor.ratelimit.RateLimitPolicy;
//
//public class RateLimitUpdateRequestDto {
//    private String key;
//    private RateLimitPolicy policy;
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    public RateLimitPolicy getPolicy() {
//        return policy;
//    }
//
//    public void setPolicy(RateLimitPolicy policy) {
//        this.policy = policy;
//    }
//}

package com.yatharth.projects.apiRateMonitor.admin;

import com.yatharth.projects.apiRateMonitor.ratelimit.RateLimitPolicy;

public class RateLimitUpdateRequestDto {

    private String key;
    private RateLimitPolicy policy;

    // 🔴 REQUIRED by Jackson
    public RateLimitUpdateRequestDto() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RateLimitPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(RateLimitPolicy policy) {
        this.policy = policy;
    }
}

