package com.springcloud.currencyexchange.repository

import com.springcloud.currencyexchange.model.CurrencyExchange
import org.springframework.data.jpa.repository.JpaRepository

interface CurrencyExchangeRepository : JpaRepository<CurrencyExchange, Long> {
    fun findByFromAndTo(from: String, to: String): CurrencyExchange?
}