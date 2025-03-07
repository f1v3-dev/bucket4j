package com.f1v3.bucket.config;

import io.github.bucket4j.Bucket;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bucket4j Configuration Class.
 *
 * @author 정승조
 * @version 2025. 03. 07.
 */
@Configuration
public class BucketConfiguration {


    @Bean
    Bucket createNewBucket() {
        return Bucket.builder()
                     .addLimit(limit ->
                             limit.capacity(10)
                                  .refillGreedy(10, Duration.ofSeconds(10)))
                     .build();
    }
}