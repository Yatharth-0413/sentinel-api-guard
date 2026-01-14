package com.yatharth.projects.apiRateMonitor.abuse;


import org.springframework.stereotype.Service;

@Service
public class AbuseDetectionService {

    private static final long SPIKE_THRESHOLD = 15; // requests / 10s

    public AbuseDecision evaluate(long requestCount) {
        if (requestCount > SPIKE_THRESHOLD) {
            return AbuseDecision.TEMP_BAN;
        }
        return AbuseDecision.ALLOW;
    }
}
