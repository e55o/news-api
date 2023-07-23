package com.nbk.news.news.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class CustomExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(RegisteredException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleRegisteredException(e: RegisteredException): ResponseError {
        logger.error(e.message, e)
        return ResponseError.ALREADY_REGISTERED
    }

    @ExceptionHandler(BadToken::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleBadTokenException(e: BadToken): ResponseError {
        logger.error(e.message, e)
        return ResponseError.BAD_CREDENTIALS
    }
}

class RegisteredException(message: String) : RuntimeException(message)

class BadToken(message: String) : RuntimeException(message)