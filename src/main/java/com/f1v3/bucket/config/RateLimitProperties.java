package com.f1v3.bucket.config;

import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Rate Limit Properties Class.
 *
 * @author 정승조
 * @version 2025. 03. 08.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "rate-limit")
public class RateLimitProperties {

    private int capacity;
    private int refillAmount;
    private Duration refillDuration;

}
