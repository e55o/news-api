package com.nbk.news.news.service

import com.nbk.news.news.dto.SignInRequestDto
import com.nbk.news.news.dto.SignInResponseDto
import com.nbk.news.news.model.User
import com.nbk.news.news.repository.UserRepository
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder

class SignInSteps {

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

    private lateinit var signInRequestDto: SignInRequestDto
    private var response: ResponseEntity<SignInResponseDto>? = null
    private var exception: Exception? = null

    init {
        MockitoAnnotations.initMocks(this)
        userService = UserService(mockUserRepository, mockPasswordEncoder, mockJwtService, mockAuthenticationManager)
    }

    @Given("a user with the following details is registered:")
    fun registerUser(dataTable: DataTable) {
        val rows = dataTable.asMaps<String, String>(String::class.java, String::class.java)
        val user = User(username = rows[0]["username"]!!, password = rows[0]["password"]!!, fullName = rows[0]["fullName"]!!)
        `when`(mockUserRepository.findByUsername(user.username)).thenReturn(user)
    }

    @When("the user attempts to sign in with the following credentials:")
    fun userAttemptsToSignIn(dataTable: DataTable) {
        val rows = dataTable.asMaps<String, String>(String::class.java, String::class.java)
        signInRequestDto = SignInRequestDto(rows[0]["username"]!!, rows[0]["password"]!!)
        `when`(mockJwtService.generateToken(mockUserRepository.findByUsername(signInRequestDto.username)!!))
            .thenReturn("token")

        val mockAuthentication = createMockAuthentication()

        `when`(mockAuthenticationManager.authenticate(any()))
            .thenReturn(mockAuthentication)
        try {
            response = userService.signIn(signInRequestDto)
            println(response);
        } catch (ex: Exception) {
            exception = ex
        }
    }

    @When("the user attempts to sign in with the following incorrect credentials:")
    fun userAttemptsToSignInWithIncorrectCredentials(dataTable: DataTable) {
        val rows = dataTable.asMaps<String, String>(String::class.java, String::class.java)
        signInRequestDto = SignInRequestDto(rows[0]["username"]!!, rows[0]["password"]!!)
        `when`(mockJwtService.generateToken(mockUserRepository.findByUsername(signInRequestDto.username)!!))
            .thenReturn("token")
        // Simulate authentication failure
        `when`(mockAuthenticationManager.authenticate(any(UsernamePasswordAuthenticationToken::class.java)))
            .thenThrow(BadCredentialsException("Authentication failed"))
        try {
            response = userService.signIn(signInRequestDto)
        } catch (ex: Exception) {
            exception = ex
        }
    }

    @Then("the user should be successfully signed in with a valid token")
    fun checkSuccessfulSignIn() {
        assertEquals("token", response?.body?.token)
    }

    @Then("the sign-in should fail with an error message {string}")
    fun checkSignInFailure(errorMessage: String) {
        assertEquals(errorMessage, exception?.message)
    }

    private fun createMockAuthentication(): Authentication {
         val user = User(username = "testUser", password = "testPassword")
         return UsernamePasswordAuthenticationToken(user, "testPassword", user.authorities)
    }
}