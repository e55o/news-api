package com.nbk.news.news.service

import com.nbk.news.news.client.NewsApiHttpClient
import com.nbk.news.news.config.AppProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class NewsService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private val newsHttpClient = NewsApiHttpClient();

    fun getTopHeadlines(search: String?,
                                pageSize: Int?,
                                pageNumber: Int?): ResponseEntity<Any> {
        logger.info("Getting top headlines for user");

        val queryMap = mutableMapOf(
            "page" to pageNumber.toString(),
            "pageSize" to pageSize.toString(),
            "country" to "us");

        if(search != null) {
            queryMap["q"] = search
        }
        var response: Any? = null;
        try {
            response = newsHttpClient.get("/v2/top-headlines", queryMap);

        } catch (exception :Exception) {
            logger.error(exception.toString());
        }

        return ResponseEntity.ok(response);
    }

    fun getSources(): ResponseEntity<Any> {
        logger.info("Getting sources for user");

        var response: Any? = null;
        try {
            response = newsHttpClient.get("/v2/top-headlines/sources");

        } catch (exception :Exception) {
            logger.error(exception.toString());
        }

        return ResponseEntity.ok(response);
    }
}