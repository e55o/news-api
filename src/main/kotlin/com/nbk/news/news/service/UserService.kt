package com.nbk.news.news.service

import com.nbk.news.news.dto.SignInRequestDto
import com.nbk.news.news.dto.SignInResponseDto
import com.nbk.news.news.dto.SignUpDto
import com.nbk.news.news.dto.SignUpResponseDto
import com.nbk.news.news.exception.RegisteredException
import com.nbk.news.news.model.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import com.nbk.news.news.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException.UnprocessableEntity
import java.time.LocalDateTime

@Service
class UserService(private val userRepository: UserRepository,
                  private val passwordEncoder: PasswordEncoder,
                  private val jwtService: JwtService,
                  private val authenticationManager: AuthenticationManager
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(UnprocessableEntity::class)
    fun signup(signUpDto: SignUpDto): ResponseEntity<SignUpResponseDto> {
        if (userRepository.findByUsername(signUpDto.username) != null) {
            throw RegisteredException("User already registered")
        }

        logger.info("Registering a new user with username: {}", signUpDto.username);
        val user = User(
            fullName = signUpDto.fullName,
            username = signUpDto.username,
            password = passwordEncoder.encode(signUpDto.password)
        )

        userRepository.save(user)

        logger.info("ser with username {} completed his registration", signUpDto.username);
        return ResponseEntity.ok(SignUpResponseDto(fullName = signUpDto.fullName, username = signUpDto.username))
        }

    @ExceptionHandler(AuthenticationException::class)
    fun signIn(signInRequestDto: SignInRequestDto): ResponseEntity<SignInResponseDto> {
        logger.info("Signing in user {}", signInRequestDto.username);
        val user = userRepository.findByUsername(signInRequestDto.username);

        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                user?.username,
                signInRequestDto.password,
                user?.authorities
            )
        )

        SecurityContextHolder.getContext().authentication = authentication

        logger.info("User {} authenticated successfully", signInRequestDto.username)

        val token = jwtService.generateToken(user!!)

        userRepository.updateTokenByUsername(token, LocalDateTime.now(), signInRequestDto.username)
        logger.info("User {} record updated successfully with new token value and date", signInRequestDto.username)
        return ResponseEntity.ok().body(SignInResponseDto(token))
    }
}