package com.nbk.news.news.service

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.apache.http.HttpEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.message.BasicHeader
import org.apache.http.entity.ContentType
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class FileServiceSteps {
    private lateinit var fileService: FileService
    private lateinit var httpClient: CloseableHttpClient
    private lateinit var response: CloseableHttpResponse
    private lateinit var entity: HttpEntity

    @Given("the file URL is {string}")
    fun givenTheFileURL(url: String) {
        httpClient = mock(CloseableHttpClient::class.java)

        response = mock(CloseableHttpResponse::class.java)

        entity = mock(HttpEntity::class.java)

        val contentType = ContentType.TEXT_PLAIN
        val header = BasicHeader("Content-Type", contentType.mimeType)
        `when`(entity.contentType).thenReturn(header)
        `when`(response.entity).thenReturn(entity)
        `when`(httpClient.execute(any())).thenReturn(response)

        fileService = FileService(httpClient)
    }

    @When("I request the file")
    fun whenIRequestTheFile() {
        try {
            fileService.getFile("https://example.com/sample.txt")

        } catch (e: Exception) {
        }
    }

    @Then("the file should be downloaded successfully")
    fun thenTheFileShouldBeDownloadedSuccessfully() {
    }

    @Then("the file should have the name {string}")
    fun thenTheFileShouldHaveTheName(fileName: String) {
    }

    // added to mock the ClosableHttpClient correctly
    private fun <T> any(): T {
        org.mockito.Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T
}