package com.nbk.news.news.controller

import com.nbk.news.news.dto.FileDto
import com.nbk.news.news.service.FileService
import org.slf4j.LoggerFactory
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/api/file")
class FileController(val fileService: FileService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping()
    fun getFile(@ModelAttribute request: FileDto): ResponseEntity<InputStreamResource> {
        logger.info("Downloading file from {}", request.url);
        return fileService.getFile(request.url);
    }
}