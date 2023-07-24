package com.nbk.news.news.controller

import com.nbk.news.news.dto.TopHeadlinesRequestDto
import com.nbk.news.news.service.NewsService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/api/news")
class NewsController(val newsService: NewsService) {
    private val logger = LoggerFactory.getLogger(javaClass)


    @GetMapping("/top-headlines")
    fun getTopHeadlines(@ModelAttribute request: TopHeadlinesRequestDto?): ResponseEntity<Any> {
        logger.info("Fetching top headlines");
        return newsService.getTopHeadlines(request?.search, request?.pageSize, request?.pageNumber);
    }

    @GetMapping("/sources")
    fun getSources(): ResponseEntity<Any> {
        logger.info("Fetching sources");
        return newsService.getSources();
    }
}