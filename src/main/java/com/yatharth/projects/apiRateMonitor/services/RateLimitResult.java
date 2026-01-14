package com.yatharth.projects.apiRateMonitor.services;

public class RateLimitResult {

    private final boolean allowed;
    private final int remaining;

    public RateLimitResult(boolean allowed, int remaining) {
        this.allowed = allowed;
        this.remaining = remaining;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public int getRemaining() {
        return remaining;
    }
}

