import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.mockito.Mockito.mock
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class CucumberSpringContextConfiguration {

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return mock(AuthenticationManager::class.java)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return mock(PasswordEncoder::class.java)
    }
}