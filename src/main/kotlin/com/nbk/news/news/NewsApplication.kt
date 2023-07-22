package com.nbk.news.news

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
@EnableWebSecurity
class NewsApplication

fun main(args: Array<String>) {
	runApplication<NewsApplication>(*args)
}
