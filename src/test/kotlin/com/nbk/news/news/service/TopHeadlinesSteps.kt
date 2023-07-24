package com.nbk.news.news.service

import com.nbk.news.news.client.NewsApiHttpClient
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

class TopHeadlinesSteps {

    @Mock
    private lateinit var mockNewsHttpClient: NewsApiHttpClient

    @InjectMocks
    private var newsService: NewsService

    private var response: ResponseEntity<Any>? = null
    private var exception: Exception? = null

    init {
        MockitoAnnotations.initMocks(this)
        newsService = NewsService()
    }

    @Given("the newsHttpClient returns a successful response")
    fun mockNewsHttpClientResponse() {
        val pageSize = 10
        val pageNumber = 1
        val queryMap = mapOf(
            "page" to pageNumber.toString(),
            "pageSize" to pageSize.toString(),
            "country" to "us"
        )
        val mockResponse = "Top headlines response";
        `when`(mockNewsHttpClient.get("/v2/top-headlines", queryMap)).thenReturn(mockResponse)
    }

    @Given("the newsHttpClient throws an exception")
    fun mockNewsHttpClientException() {
        val pageSize = 10
        val pageNumber = 1
        val queryMap = mapOf(
            "page" to pageNumber.toString(),
            "pageSize" to pageSize.toString(),
            "country" to "us"
        )
        val mockException = RuntimeException("Some exception message")
        `when`(mockNewsHttpClient.get("/v2/top-headlines", queryMap)).thenThrow(mockException)
    }

    @When("the user calls the getTopHeadlines API with pageSize {int} and pageNumber {int}")
    fun callGetTopHeadlinesAPI(pageSize: Int, pageNumber: Int) {
        try {
            response = newsService.getTopHeadlines(null, pageSize, pageNumber)
        } catch (ex: Exception) {
            exception = ex
        }
    }

    @Then("the response status should be {int} {word}")
    fun checkResponseStatus(statusCode: Int, statusText: String) {
        assertEquals(HttpStatus.valueOf(statusCode), response?.statusCode)
        assertEquals("200", response?.statusCode?.value().toString())
    }

    @Then("the response body should be {string}")
    fun checkTopHeadlinesResponseBody(expectedBody: String) {
        assertEquals(expectedBody, response?.body)
    }
}