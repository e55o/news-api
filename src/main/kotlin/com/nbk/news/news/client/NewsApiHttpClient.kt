package com.nbk.news.news.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nbk.news.news.config.AppProperties
import com.nbk.news.news.exception.ResourceNotFoundException
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.lang.Exception

@Component
@Service
class NewsApiHttpClient {
    @Autowired
    private val appProperties: AppProperties = AppProperties();

    private val client = OkHttpClient();
    private val logger = LoggerFactory.getLogger(javaClass)

    fun get(path: String, queryParams: Map<String, String> = mapOf()): Any? {
        val url = appProperties.newsApiBaseUrl + path;
        val urlBuilder = url.toHttpUrl().newBuilder();
        urlBuilder.addQueryParameter("apiKey", appProperties.apiKey);

        queryParams.forEach { (key, value) ->
            urlBuilder.addQueryParameter(key, value)
        }

        val request = Request.Builder()
            .url(urlBuilder.build())
            .build();

        logger.info("Sending request to: {}", url);
        val response = client.newCall(request).execute();
        val mapper = jacksonObjectMapper()


        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            return mapper.readValue(responseBody, Any::class.java);
        } else {
            throw ResourceNotFoundException("Error fetching data from NewsAPI: $response")
        }
    }
}