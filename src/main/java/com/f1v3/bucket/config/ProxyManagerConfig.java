package com.f1v3.bucket.config;

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.Bucket4jLettuce;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Bucket4j Proxy Manager Configuration Class.
 *
 * @author 정승조
 * @version 2025. 03. 07.
 */
@Configuration
public class ProxyManagerConfig {

    @Bean
    ProxyManager<String> lettuceBasedProxyManager(RedisClient redisClient) {
        StatefulRedisConnection<String, byte[]> redisConnection =
                redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));

        return Bucket4jLettuce
                .casBasedBuilder(redisConnection)
                .expirationAfterWrite(ExpirationAfterWriteStrategy
                        .basedOnTimeForRefillingBucketUpToMax(Duration.ofSeconds(10)))
                .build();
    }
}