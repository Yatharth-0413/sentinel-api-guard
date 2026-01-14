package com.yatharth.projects.apiRateMonitor.security;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class UserContextResolver {

    public String resolveUserId(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        // Simulated JWT parsing
        return header.substring(7); // token == userId (for now)
    }
}
