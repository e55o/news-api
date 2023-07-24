package com.nbk.news.news.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ControllerAdvice
class CustomExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(RegisteredException::class)
    fun handleRegisteredException(ex: RegisteredException): ResponseEntity<String> {
        logger.error(ex.message, ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }

    @ExceptionHandler(BadTokenException::class)
    fun handleBadTokenException(ex: BadTokenException): ResponseEntity<String> {
        logger.error(ex.message, ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<String> {
        logger.error(ex.message, ex)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(message: String) : Exception(message)

@ResponseStatus(HttpStatus.CONFLICT)
class RegisteredException(message: String) : Exception(message)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class BadTokenException(message: String) : Exception(message)