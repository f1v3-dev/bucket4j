package com.f1v3.bucket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
public class RateLimitController {

    @GetMapping
    public ResponseEntity<String> testRateLimit() {
        log.info("check rate limited api");
        return ResponseEntity.ok().body("API 호출 성공!");
    }
}
