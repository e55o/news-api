package com.nbk.news.news.controller

import com.nbk.news.news.dto.FileDto
import com.nbk.news.news.service.FileService
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.ResponseEntity

class FileControllerSteps {

    @Mock
    private lateinit var fileService: FileService

    @InjectMocks
    private var fileController: FileController

    private var response: ResponseEntity<ByteArrayResource>? = null
    private var exception: Exception? = null

    init {
        MockitoAnnotations.initMocks(this)
        fileController = FileController(fileService)
    }

    @Given("any request to file controller")
    fun anyRequest(dataTable: DataTable) {
    }

    @When("the user attempts to download a file from url")
    fun userDownloadsAFileFromUrl() {
        val url = "https://www.africau.edu/images/default/sample.pdf"
        try {
            response = fileController.getFile(FileDto(url = url))
            println(response);
        } catch (ex: Exception) {
            exception = ex
        }
    }


    @Then("the user should get successful response")
    fun successfulFileDownload() {
    }
}