package com.nbk.news.news.controller

import com.nbk.news.news.dto.SignInRequestDto
import com.nbk.news.news.dto.SignInResponseDto
import com.nbk.news.news.service.UserService
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.http.ResponseEntity

class SignInControllerSteps {

    @Mock
    private lateinit var userService: UserService

    @InjectMocks
    private var userController: UserController

    private lateinit var signInRequestDto: SignInRequestDto
    private var response: ResponseEntity<SignInResponseDto>? = null
    private var exception: Exception? = null

    init {
        MockitoAnnotations.initMocks(this)
        userController = UserController(userService)
    }

    @Given("any request to signIn controller")
    fun signInControllerSetup() {
    }

    @When("user attempts to sign in with the following credentials:")
    fun userAttemptsToSignIn(dataTable: DataTable) {
        val rows = dataTable.asMaps<String, String>(String::class.java, String::class.java)
        signInRequestDto = SignInRequestDto(rows[0]["username"]!!, rows[0]["password"]!!)

        try {
            response = userController.signIn(signInRequestDto)
            println(response);
        } catch (ex: Exception) {
            exception = ex
        }
    }

    @Then("user should be successfully signed in with a valid token")
    fun checkSuccessfulSignIn() {
    }
}