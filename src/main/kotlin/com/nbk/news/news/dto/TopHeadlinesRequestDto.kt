package com.nbk.news.news.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Positive

data class TopHeadlinesRequestDto(
    @field:Positive(message = "Page size must be positive")
    @field:Max(value = 100, message = "Page size can have a maximum of 100")
    val pageSize: Int,

    @field:Positive(message = "Page Number must be positive")
    val pageNumber: Int,

    val search: String? = null,
)
