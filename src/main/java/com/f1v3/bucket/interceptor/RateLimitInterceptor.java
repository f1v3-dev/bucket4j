package com.f1v3.bucket.interceptor;

import com.f1v3.bucket.util.RateLimiter;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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

        String clientKey = request.getRemoteAddr();

        // todo: 남아있는 토큰의 수도 반환해서 로그 처리
        ConsumptionProbe probe = rateLimiter.checkRateLimit(clientKey);

        if (probe.isConsumed()) {
            long remainingTokens = probe.getRemainingTokens();
            response.addHeader("X-Rate-Limit-Remaining", Long.toString(remainingTokens));
            log.info("Success to consume 1 token! Remaining tokens: {}", remainingTokens);
            return true;
        }


        response.setStatus(429);
        log.info("Failed to consume 1 token !");
        return false;
    }
}
