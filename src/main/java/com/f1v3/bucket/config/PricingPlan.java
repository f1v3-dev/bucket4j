package com.f1v3.bucket.config;

import io.github.bucket4j.Bandwidth;
import java.time.Duration;

/**
 * API Client Pricing Plan Enum.
 *
 * <p>
 *     Ref. <a href="https://www.baeldung.com/spring-bucket4j">baeldung - bucket4j</a>
 * </p>
 *
 * @author 정승조
 * @version 2025. 03. 07.
 */
public enum PricingPlan {

    FREE {
        public Bandwidth getLimit() {
            return Bandwidth.builder()
                            .capacity(3)
                            .refillIntervally(3, Duration.ofHours(1))
                            .build();
        }
    },

    BASIC {
        public Bandwidth getLimit() {
            return Bandwidth.builder()
                            .capacity(5)
                            .refillIntervally(5, Duration.ofMinutes(1))
                            .build();
        }
    },

    PREMIUM {
        public Bandwidth getLimit() {
            return Bandwidth.builder()
                            .capacity(10)
                            .refillIntervally(10, Duration.ofMinutes(1))
                            .build();
        }
    };

    public abstract Bandwidth getLimit();

    public static PricingPlan resolvePlanFromApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            return FREE;
        }

        if (apiKey.startsWith("basic-")) {
            return BASIC;
        }

        if (apiKey.startsWith("premium-")) {
            return PREMIUM;
        }

        return FREE;
    }
}
