package com.yatharth.projects.apiRateMonitor.ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RateLimitKeyGenerator {



    public String generateKey(
            RateLimitDimension dimension,
            HttpServletRequest request,
            String userId
    ) {

        String ip = extractClientIp(request);
        String endpoint = request.getRequestURI();

        return switch (dimension) {
            case IP ->
                    "rate_limit:IP:" + ip;

            case ENDPOINT ->
                    "rate_limit:ENDPOINT:" + endpoint;

            case IP_ENDPOINT ->
                    "rate_limit:IP_ENDPOINT:" + ip + ":" + endpoint;

            case USER ->
                    "rate_limit:USER:" + userId;

            case USER_ENDPOINT ->
                    "rate_limit:USER_ENDPOINT:" + userId + ":" + endpoint;
        };
    }



    private String extractClientIp(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
