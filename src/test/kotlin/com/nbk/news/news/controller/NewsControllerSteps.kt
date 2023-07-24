package com.nbk.news.news.controller

import com.nbk.news.news.dto.TopHeadlinesRequestDto
import com.nbk.news.news.service.NewsService
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.http.ResponseEntity

class NewsControllerSteps {
    @Mock
    private lateinit var newsService: NewsService

    @InjectMocks
    private var newsController: NewsController

    private var response: ResponseEntity<Any>? = null
    private var exception: Exception? = null

    init {
        MockitoAnnotations.initMocks(this)
        newsController = NewsController(newsService)
    }

    @Given("any request to news controller")
    fun loadRegisteredUser(dataTable: DataTable) {
    }

    @When("user attempts to call sources endpoint")
    fun userFetchingSources() {
        try {
            response = newsController.getSources()
            println(response);
        } catch (ex: Exception) {
            exception = ex
        }
    }

    @When("user attempts to call top headlines endpoint")
    fun userFetchingTopHeadlines() {
        try {
            response = newsController.getTopHeadlines(TopHeadlinesRequestDto(pageNumber = 1, pageSize = 10));
            println(response);
        } catch (ex: Exception) {
            exception = ex
        }
    }


    @Then("response should be successful")
    fun checkSuccessfulFetchData() {
    }
}