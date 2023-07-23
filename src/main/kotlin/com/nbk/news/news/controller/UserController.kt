package com.nbk.news.news.controller

import com.nbk.news.news.dto.SignInRequestDto
import com.nbk.news.news.dto.SignInResponseDto
import com.nbk.news.news.dto.SignUpDto
import com.nbk.news.news.dto.SignUpResponseDto
import com.nbk.news.news.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/api/public/users")
class UserController(val userService: UserService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignUpDto): ResponseEntity<SignUpResponseDto> {
        logger.info("Signing up user with username {}", request.username);
        return userService.signup(request);
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody request: SignInRequestDto): ResponseEntity<SignInResponseDto> {
        logger.info("Signing in user with username {}", request.username);
        return userService.signIn(request);
    }
}