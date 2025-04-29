package com.adote.api.infra.service;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


@Service
public class RateLimiterService {

    private final Cache<String, Integer> ipRequestCache;
    private static final int MAX_REQUESTS_PER_HOUR = 10;

    public RateLimiterService(Cache<String, Integer> ipRequestCache) {
        this.ipRequestCache = ipRequestCache;
    }

    public boolean isAllowed(HttpServletRequest request) {
        String ip = extractIpFromRequest(request);
        Integer requestCount = ipRequestCache.getIfPresent(ip);

        if (requestCount == null) {
            ipRequestCache.put(ip, 1);
            return true;
        }

        if (requestCount < MAX_REQUESTS_PER_HOUR) {
            ipRequestCache.put(ip, requestCount + 1);
            return true;
        }

        return false;
    }

    private String extractIpFromRequest(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isEmpty()) {
            return forwardedFor.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}