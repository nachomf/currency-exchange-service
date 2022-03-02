package com.springcloud.currencyexchange.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

class CurrencyExchangeNotFoundException(message: String) : RuntimeException(message)

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [CurrencyExchangeNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handle(e: CurrencyExchangeNotFoundException) = errorMessage(e, "currency_exchange_not_found")

    fun errorMessage(exception: Throwable, code: String) = ErrorMessage(exception.message ?: "", code)

    data class ErrorMessage(val message: String, val code: String)
}