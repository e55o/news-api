package com.nbk.news.news.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppProperties {

    @Value("\${news.api.key}")
    lateinit var apiKey: String;

    @Value("\${news.api.baseUrl}")
    lateinit var newsApiBaseUrl: String;
}