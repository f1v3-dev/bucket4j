package com.f1v3.bucket.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redis Configuration Class.
 *
 * @author 정승조
 * @version 2025. 03. 08.
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    RedisClient redisClient() {
        return RedisClient.create(
                RedisURI.builder()
                        .withHost(host)
                        .withPort(port)
                        .build());
    }
}
