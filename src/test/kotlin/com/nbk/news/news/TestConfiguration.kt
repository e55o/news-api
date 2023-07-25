package com.nbk.news.news

import com.nbk.news.news.config.AppProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class TestConfiguration {
    @Bean
    @Primary
    fun appProperties(): AppProperties {
        val appProperties = AppProperties()
        appProperties.apiKey = "api_key"
        appProperties.newsApiBaseUrl = "http://example.com/api/"
        appProperties.secretKey = "secret_key"
        appProperties.expirationTime = 300000
        return appProperties
    }

}