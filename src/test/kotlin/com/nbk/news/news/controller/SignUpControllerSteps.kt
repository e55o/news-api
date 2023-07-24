package com.nbk.news.news.controller

import com.nbk.news.news.dto.SignUpDto
import com.nbk.news.news.dto.SignUpResponseDto
import com.nbk.news.news.service.UserService
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.ResponseEntity

class SignUpControllerSteps {
    @Mock
    private lateinit var mockSignupService: UserService

    @InjectMocks
    private var signupController: UserController

    private lateinit var signUpDto: SignUpDto
    private var response: ResponseEntity<SignUpResponseDto>? = null
    private var exception: Exception? = null

    init {
        MockitoAnnotations.initMocks(this)
        signupController = UserController(mockSignupService)
    }

    @Given("any request to signUp controller")
    fun createSignUpDto(dataTable: DataTable) {
        val rows = dataTable.asMaps<String, String>(String::class.java, String::class.java)
        val row = rows[0]
        signUpDto = SignUpDto(row["fullName"]!!, row["username"]!!, row["password"]!!)
    }

    @When("the user attempts to signup")
    fun userAttemptsToSignup() {
        `when`(mockSignupService.signup(signUpDto)).thenReturn(ResponseEntity.ok().body(SignUpResponseDto(signUpDto.fullName, signUpDto.username)))
        try {
            response = signupController.signup(signUpDto)
        } catch (ex: Exception) {
            exception = ex
        }
    }

    @Then("the signup should succeed")
    fun checkSignupFailure() {
    }
}