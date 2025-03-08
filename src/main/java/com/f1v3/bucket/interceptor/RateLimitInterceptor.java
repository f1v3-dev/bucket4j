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

        ConsumptionProbe prob = rateLimiter.checkRateLimit(clientKey);

        if (prob.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", Long.toString(prob.getRemainingTokens()));
            log.info("Success to consume 1 token !");
            return true;
        }


        response.setStatus(429);
        log.info("Failed to consume 1 token !");
        return true;
    }


}
