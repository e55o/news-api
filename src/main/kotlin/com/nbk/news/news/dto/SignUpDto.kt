package com.nbk.news.news.dto

import org.hibernate.validator.constraints.Length
import org.intellij.lang.annotations.Pattern

data class SignUpDto(
    @field:Length(max = 20, message = "First Name should have a maximum of 20 characters")
    val fullName: String,

    @field:Length(max = 20, message = "First Name should have a maximum of 20 characters")
    @field:Pattern(value = "/^[a-zA-Z0-9_.-]{3,20}\$/")
    val username: String,

    @field:Length(min = 8, message = "Password should have a minimum of 20 characters")
    @field:Pattern(value = "/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+[\\]{}|\\\\:;\"'<>,.?/~`]).{8,}\$/")
    val password: String,
)
