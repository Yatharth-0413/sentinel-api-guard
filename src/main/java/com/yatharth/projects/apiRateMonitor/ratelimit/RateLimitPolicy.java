package com.yatharth.projects.apiRateMonitor.ratelimit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RateLimitPolicy {

    private final int capacity;
    private final double refillRate;

    @JsonCreator
    public RateLimitPolicy(
            @JsonProperty("capacity") int capacity,
            @JsonProperty("refillRate") double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
    }


    public int getCapacity() {
        return capacity;
    }

    public double getRefillRate() {
        return refillRate;
    }
}
