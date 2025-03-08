package com.f1v3.bucket.util;

import com.f1v3.bucket.config.RateLimitProperties;
import io.github.bucket4j.BucketConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Rate Limit Policy Class.
 *
 * @author 정승조
 * @version 2025. 03. 08.
 */
@Component
@RequiredArgsConstructor
public class RateLimitPolicy {

    private final RateLimitProperties properties;

    public BucketConfiguration createBucketConfig() {
        return BucketConfiguration.builder()
                .addLimit(limit -> limit
                        .capacity(properties.getCapacity())
                        .refillIntervally(properties.getRefillAmount(), properties.getRefillDuration())
                )
                .build();
    }
}
