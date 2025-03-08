package com.f1v3.bucket.interceptor;

import com.f1v3.bucket.util.RateLimiter;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * Rate Limit Interceptor Class.
 *
 * @author 정승조
 * @version 2025. 03. 08.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiter rateLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String clientKey = request.getRemoteAddr(); // 클라이언트 IP 기반 키
        ConsumptionProbe probe = rateLimiter.checkRateLimit(clientKey);

        if (probe.isConsumed()) {
            handleAllowedRequest(response, probe);
            return true;
        }

        handleRateLimitedRequest(response, probe);
        return false;

    }

    /**
     * 허용된 요청 처리
     *
     * @param response HttpServletResponse
     * @param probe    ConsumptionProbe
     */
    private void handleAllowedRequest(HttpServletResponse response, ConsumptionProbe probe) {
        long remainingTokens = probe.getRemainingTokens();
        response.addHeader("X-Rate-Limit-Remaining", Long.toString(remainingTokens));
        log.info("Success to consume 1 token! Remaining tokens: {}", remainingTokens);
    }

    /**
     * 거부된 요청 처리
     *
     * @param response HttpServletResponse
     * @param probe    ConsumptionProbe
     */
    private void handleRateLimitedRequest(HttpServletResponse response, ConsumptionProbe probe)
            throws IOException {
        long waitTime = probe.getNanosToWaitForRefill() / 1_000_000_000; // 나노초 → 초 변환
        response.setStatus(429);
        response.addHeader("X-Rate-Limit-Retry-After-Seconds", Long.toString(waitTime)); // 재시도 대기 시간
        response.getWriter().write("Rate limit exceeded. Try again in " + waitTime + " seconds.");
        log.warn("Failed to consume 1 token! Wait time: {} seconds", waitTime);
    }
}
