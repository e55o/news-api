package com.nbk.news.news.dto

import org.hibernate.validator.constraints.URL

data class FileDto(
    @field:URL(message = "Parameter must be a valid url")
    val url: String,
)
