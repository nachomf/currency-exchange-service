package com.springcloud.currencyexchange.model

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CurrencyExchange(
    @Id
    val id: Long,
    @Column(name = "currency_from")
    val from: String,
    @Column(name = "currency_to")
    val to: String,
    val conversionMultiple: BigDecimal,
    val environment: String?
)