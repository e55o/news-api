package com.nbk.news.news.service

import com.nbk.news.news.client.NewsApiHttpClient
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class SourcesSteps {

    @Mock
    private lateinit var mockNewsHttpClient: NewsApiHttpClient

    @InjectMocks
    private var newsService: NewsService

    private var response: ResponseEntity<Any>? = null
    private var exception: Exception? = null

    init {
        MockitoAnnotations.initMocks(this)
        newsService = NewsService(mockNewsHttpClient)
    }

    init {
        MockitoAnnotations.initMocks(this)
        newsService = NewsService(mockNewsHttpClient)
    }

    @Given("the newsHttpClient returns a successful response for getting sources")
    fun mockNewsHttpClientResponse() {
        val mockResponse = ResponseEntity.ok("Sources response")
        `when`(mockNewsHttpClient.get("/v2/top-headlines/sources")).thenReturn(mockResponse)
    }

    @Given("the newsHttpClient throws an exception for getSources")
    fun newsHttpClientException() {
        val mockException = RuntimeException("Some exception message")
        `when`(mockNewsHttpClient.get("/v2/top-headlines/sources")).thenThrow(mockException)
    }

    @When("the user calls the getSources")
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

    @Then("the source service should throw exception")
    fun checkSourceResponseBody() {
        assertNotNull(exception)
    }
}