package com.yatharth.projects.apiRateMonitor.filter;

import com.yatharth.projects.apiRateMonitor.abuse.AbuseDecision;
import com.yatharth.projects.apiRateMonitor.abuse.AbuseDetectionService;
import com.yatharth.projects.apiRateMonitor.abuse.AbuseTracker;
import com.yatharth.projects.apiRateMonitor.abuse.BanService;
import com.yatharth.projects.apiRateMonitor.config.RateLimitConfig;
import com.yatharth.projects.apiRateMonitor.ratelimit.*;
import com.yatharth.projects.apiRateMonitor.security.UserContextResolver;
import com.yatharth.projects.apiRateMonitor.services.RateLimitResult;
import com.yatharth.projects.apiRateMonitor.services.RedisTokenBucketRateLimiter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.yatharth.projects.apiRateMonitor.metrics.TrafficMetrics;
import com.yatharth.projects.apiRateMonitor.metrics.TimeSeriesMetrics;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTokenBucketRateLimiter rateLimiter;

    @Autowired
    private RateLimitKeyGenerator keyGenerator;

    @Autowired
    private RateLimitPolicyRegistry policyRegistry;

    @Autowired
    private UserContextResolver userContextResolver;

    @Autowired
    private TierPolicyResolver tierPolicyResolver;

    @Autowired
    private AbuseTracker abuseTracker;

    @Autowired
    private AbuseDetectionService abuseDetectionService;

    @Autowired
    private BanService banService;

    @Autowired
    private RateLimitConfig rateLimitConfig;

    @Autowired
    private RateLimitPolicyService rateLimitPolicyService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {



        // 1️⃣ Identify endpoint
        String endpoint = request.getRequestURI();

        // CONTROL-PLANE ENDPOINTS (SKIP RATE LIMITING)
        if (endpoint.startsWith("/metrics") || endpoint.startsWith("/health") || endpoint.startsWith("/admin")) {
            filterChain.doFilter(request, response);
            return;
        }

        // adding it here , not sure exactly
        if (!rateLimitConfig.isEnabled()) {
            TrafficMetrics.allowedRequests.incrementAndGet();
            filterChain.doFilter(request, response);
            return;
        }

        TrafficMetrics.totalRequests.incrementAndGet();
        TimeSeriesMetrics.incrementRequest();

        TrafficMetrics.endpointCounts
                .computeIfAbsent(endpoint, k -> new AtomicLong())
                .incrementAndGet();

        // 2️⃣ Resolve user (if authenticated)
        String userId = userContextResolver.resolveUserId(request);

        // 3️⃣ Decide rate-limit dimension
        RateLimitDimension dimension =
                (userId != null)
                        ? RateLimitDimension.USER_ENDPOINT
                        : RateLimitDimension.IP_ENDPOINT;

        // 4️⃣ Resolve policy
//        RateLimitPolicy policy;
//        if (userId != null) {
//            UserTier tier = userId.startsWith("premium")
//                    ? UserTier.PREMIUM
//                    : UserTier.FREE;
//
//            policy = tierPolicyResolver.resolve(tier); // 2
//        } else {
//            policy = policyRegistry.resolve(endpoint); // 1
//        }

        UserTier tier =
                (userId != null && userId.startsWith("premium"))
                        ? UserTier.PREMIUM
                        : UserTier.FREE;

        RateLimitPolicy policy =
                rateLimitPolicyService.resolve(
                        dimension,
                        endpoint,
                        tier
                );



        String clientKey = (userId != null) ? userId : request.getRemoteAddr();

// 1️⃣ Check ban
        if (banService.isBanned(clientKey)) {
            TrafficMetrics.bannedRequests.incrementAndGet();
            TimeSeriesMetrics.incrementBanned();
            response.setStatus(429);
            response.getWriter().write("{\"error\":\"Temporarily banned\"}");
            return;
        }

// 2️⃣ Track behavior
        long count = abuseTracker.recordRequest(clientKey);

// 3️⃣ Detect abuse
        AbuseDecision decision = abuseDetectionService.evaluate(count);

        if (decision == AbuseDecision.TEMP_BAN) {
            banService.ban(clientKey);
            response.setStatus(429);
            response.getWriter().write("{\"error\":\"Abuse detected. Temporarily blocked.\"}");
            return;
        }


        // 5️⃣ Generate Redis key
        String redisKey = keyGenerator.generateKey(
                dimension,
                request,
                userId
        );




        // 6️⃣ Call Redis rate limiter (CORRECT)
        RateLimitResult result = rateLimiter.allowRequest(
                redisKey,
                policy.getCapacity(),
                policy.getRefillRate(),
                rateLimitConfig.getFailureMode()
        );

        // 7️⃣ Set headers
        response.setHeader("X-Rate-Limit-Limit",
                String.valueOf(policy.getCapacity()));
        response.setHeader("X-Rate-Limit-Remaining",
                String.valueOf(result.getRemaining()));

        // 8️⃣ Block if exceeded
        if (!result.isAllowed()) {
            TrafficMetrics.rateLimitedRequests.incrementAndGet();
            TimeSeriesMetrics.incrementRateLimited();
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Too many requests\"}"
            );
            return;
        }

        // 9️⃣ Allow request
        TrafficMetrics.allowedRequests.incrementAndGet();
//        TimeSeriesMetrics.incrementRequest();
        filterChain.doFilter(request, response);
    }
}
