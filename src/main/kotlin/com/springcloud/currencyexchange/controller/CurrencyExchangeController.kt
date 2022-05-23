package com.springcloud.currencyexchange.controller

import com.springcloud.currencyexchange.exception.CurrencyExchangeNotFoundException
import com.springcloud.currencyexchange.model.CurrencyExchange
import com.springcloud.currencyexchange.repository.CurrencyExchangeRepository
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyExchangeController(
    private val env: Environment,
    private val currencyExchangeRepository: CurrencyExchangeRepository
) {

    companion object {
        val logger = LoggerFactory.getLogger(CurrencyExchangeController::class.java)
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    fun getCurrencyExchange(
        @PathVariable from: String,
        @PathVariable to: String,
    ): CurrencyExchange = currencyExchangeRepository
        .apply { logger.info("Currency exchange request from {} to {}", from, to) }
        .findByFromAndTo(from, to)
        ?.copy(environment = env.getProperty("local.server.port"))
        ?: throw CurrencyExchangeNotFoundException("Currency exchange from $from to $to not found")
}