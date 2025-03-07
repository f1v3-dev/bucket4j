package com.f1v3.bucket.controller;

import io.github.bucket4j.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rate Limit Test Controller.
 *
 * @author 정승조
 * @version 2025. 03. 07.
 */
@Slf4j
@RestController
@RequestMapping("/api/rate-limit")
public class TestController {

    private final Bucket bucket;

    public TestController(Bucket bucket) {
        this.bucket = bucket;
    }

    @GetMapping
    public ResponseEntity<Void> testRateLimit() {

        if (bucket.tryConsume(1)) {
            log.info("Success to consume 1 token !");
            return ResponseEntity.ok().build();
        }

        log.info("TOO MANY REQUESTS !");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }


}
