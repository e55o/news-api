package com.nbk.news.news.service

import com.nbk.news.news.config.AppProperties
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import junit.framework.TestCase.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

@SpringBootTest
class JwtSteps {
    @Mock
    private lateinit var appProperties: AppProperties

    @InjectMocks
    private lateinit var jwtService: JwtService

    private lateinit var userDetails: UserDetails
    private var generatedToken: String? = null

    init {
        MockitoAnnotations.initMocks(this)
        jwtService = JwtService(appProperties)
    }

    @Given("the user details:")
    fun theUserDetails(userTable: DataTable) {
        val rows = userTable.asMaps<String, String>(String::class.java, String::class.java)
        val username = rows[0]["username"] ?: error("Missing username in the DataTable")
        val password = rows[0]["password"] ?: error("Missing password in the DataTable")
        userDetails = User(username, password, emptyList())

    }

    @When("the JWT token is generated")
    fun theJwtTokenIsGenerated() {
        `when`(appProperties.expirationTime).thenReturn(300000)
        `when`(appProperties.secretKey).thenReturn("n7P7gy0NPivAw1q/OGkl3JlHxQTsta2unJ3nxkUwgxRQUWk=")

        generatedToken = jwtService.generateToken(userDetails)
    }

    @Then("the token should be non-empty")
    fun theTokenShouldBeNonEmpty() {
        assertNotNull(generatedToken)
        assertTrue(generatedToken!!.isNotEmpty())
    }

    @When("the token is validated with the user details")
    fun theTokenIsValidatedWithTheUserDetails() {
        val isValid = jwtService.isTokenValid(generatedToken!!, userDetails)
        assertTrue(isValid)
    }

    @Then("the token should be valid")
    fun theTokenShouldBeValid() {
        val isValid = jwtService.isTokenValid(generatedToken!!, userDetails)
        assertTrue(isValid)
    }
}