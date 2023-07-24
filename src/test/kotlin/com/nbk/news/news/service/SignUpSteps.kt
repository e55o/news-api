package com.nbk.news.news.service

import com.nbk.news.news.dto.SignUpDto
import com.nbk.news.news.dto.SignUpResponseDto
import com.nbk.news.news.repository.UserRepository
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import junit.framework.TestCase.assertEquals
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder

class SignUpSteps {
    @Mock
    private lateinit var mockUserRepository: UserRepository

    @Mock
    private lateinit var mockPasswordEncoder: PasswordEncoder

    @Mock
    private lateinit var mockAuthenticationManager: AuthenticationManager

    @Mock
    private lateinit var mockJwtService: JwtService

    @InjectMocks
    private var userService: UserService

    private lateinit var signUpDto: SignUpDto
    private var response: ResponseEntity<SignUpResponseDto>? = null
    private var exception: Exception? = null

    init {
        MockitoAnnotations.initMocks(this)
        userService = UserService(mockUserRepository, mockPasswordEncoder, mockJwtService, mockAuthenticationManager)
    }

    @Given("a new user with the following details registering:")
    fun createSignUpDto(dataTable: DataTable) {
        val rows = dataTable.asMaps<String, String>(String::class.java, String::class.java)
        val row = rows[0]
        signUpDto = SignUpDto(row["fullName"]!!, row["username"]!!, row["password"]!!)
    }

    @When("user attempts to signup")
    fun userAttemptsToSignup() {
        `when`(mockPasswordEncoder.encode(anyString())).thenReturn("Password@123")
        try {
            response = userService.signup(signUpDto)
        } catch (ex: Exception) {
            exception = ex
        }
    }

    @Then("user should be successfully registered with the following response:")
    fun checkSuccessfulRegistration(dataTable: DataTable) {
        val rows = dataTable.asMaps<String, String>(String::class.java, String::class.java)
        val row = rows[0]
        val expectedResponse = SignUpResponseDto(row["fullName"]!!, row["username"]!!)

        assertEquals(expectedResponse, response?.body)
    }

}