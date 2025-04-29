package com.adote.api.infra.service;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RateLimiterServiceTest {

    private RateLimiterService rateLimiterService;
    private HttpServletRequest mockRequest;
    private Cache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = Mockito.mock(Cache.class);
        rateLimiterService = new RateLimiterService(cache);
        mockRequest = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    void testFirstRequestIsAllowed() {
        Mockito.when(mockRequest.getRemoteAddr()).thenReturn("200.100.50.25");

        Mockito.when(cache.getIfPresent("200.100.50.25")).thenReturn(null);

        boolean allowed = rateLimiterService.isAllowed(mockRequest);
        assertTrue(allowed, "A primeira requisição deveria ser permitida");

        Mockito.verify(cache).put("200.100.50.25", 1);
    }

    @Test
    void testSubsequentRequestsAreAllowedUntilLimit() {
        Mockito.when(mockRequest.getRemoteAddr()).thenReturn("200.100.50.25");

        for (int i = 1; i <= 9; i++) {
            Mockito.when(cache.getIfPresent("200.100.50.25")).thenReturn(i);
            assertTrue(rateLimiterService.isAllowed(mockRequest), "A requisição " + (i + 1) + " deveria ser permitida");
        }

        Mockito.verify(cache, Mockito.times(9)).put(Mockito.eq("200.100.50.25"), Mockito.anyInt());
    }

    @Test
    void testExceedingLimitShouldBlock() {
        Mockito.when(mockRequest.getRemoteAddr()).thenReturn("200.100.50.25");

        for (int i = 1; i <= 10; i++) {
            Mockito.when(cache.getIfPresent("200.100.50.25")).thenReturn(i - 1);
            rateLimiterService.isAllowed(mockRequest);
        }

        Mockito.when(cache.getIfPresent("200.100.50.25")).thenReturn(10);

        boolean allowed = rateLimiterService.isAllowed(mockRequest);
        assertFalse(allowed, "A 11ª requisição deveria ser bloqueada");
    }
}