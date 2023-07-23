package com.nbk.news.news.service

import com.nbk.news.news.config.AppProperties
import com.nbk.news.news.exception.BadToken
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.lang.Exception
import java.security.Key
import java.util.*

@Service
class JwtService @Autowired constructor(private val appProperties: AppProperties) {

    fun extractUsername(token: String): String = extractClaim(token, Claims::getSubject)

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String = Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.username)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + appProperties.expirationTime.toLong()))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact()

    private fun extractAllClaims(token: String): Claims {
        try {
            return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            throw BadToken("Invalid Token");
        }
    }

    fun generateToken(userDetails: UserDetails): String = generateToken(HashMap(), userDetails)

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(appProperties.secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}