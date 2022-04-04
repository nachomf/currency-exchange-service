package com.springcloud.currencyexchange.controller

import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import io.github.resilience4j.retry.annotation.Retry
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class CircuitBreakerController {

    companion object {
        val logger = LoggerFactory.getLogger(Companion::class.java)
    }

    @GetMapping("/sample-api")
    @Retry(name = "sample-api", fallbackMethod = "handleErrors")
    //@CircuitBreaker(name = "sample-api", fallbackMethod = "handleErrors")
    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    fun sampleApi(): String? {
        logger.info("Sampler api call received")
        val forEntity = RestTemplate().getForEntity("http://localhost:8123/retry-error", String::class.java)

        return forEntity.body
    }

    fun handleErrors(e: Exception) = "fallback-method" + e.message
}