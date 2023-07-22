package com.nbk.news.news.repository

import com.nbk.news.news.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    @Modifying
    @Transactional
    @Query("update User u set u.token = ?1, u.tokenCreationDate = ?2 where u.username = ?3")
    fun updateTokenByUsername(token: String, tokenCreationDateTime: LocalDateTime,username: String);
}