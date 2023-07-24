package com.nbk.news.news.service

import com.nbk.news.news.client.NewsApiHttpClient
import com.nbk.news.news.config.AppProperties
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.Assert.assertEquals
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class SourcesSteps {

    @Mock
    private lateinit var mockNewsHttpClient: NewsApiHttpClient

    @Mock
    private lateinit var appProperties: AppProperties

    @InjectMocks
    private var newsService: NewsService

    private var response: ResponseEntity<Any>? = null
    private var exception: Exception? = null

    init {
        MockitoAnnotations.initMocks(this)
        newsService = NewsService()
    }

    @Given("the newsHttpClient returns a successful response for getting sources")
    fun mockNewsHttpClientResponse() {
        val mockResponse = ResponseEntity.ok("Sources response")
        `when`(appProperties.newsApiBaseUrl).thenReturn("https://newsapi.org");
        `when`(mockNewsHttpClient.get("/v2/top-headlines/sources")).thenReturn(mockResponse)
    }

    @When("the user calls the getSources API")
    fun callGetSourcesAPI() {
        try {
            response = newsService.getSources()
        } catch (ex: Exception) {
            exception = ex
        }
    }

    @Then("the source response status should be {int} {word}")
    fun checkResponseStatus(statusCode: Int, statusText: String) {
        assertEquals(HttpStatus.valueOf(statusCode), response?.statusCode)
    }

    @Then("the source response body should be {string}")
    fun checkSourceResponseBody(expectedBody: String) {
        assertEquals(expectedBody, response?.body)
    }
}