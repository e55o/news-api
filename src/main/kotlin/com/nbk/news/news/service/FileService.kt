package com.nbk.news.news.service

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.slf4j.LoggerFactory
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FileService(private val httpClient: CloseableHttpClient) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getFile(url: String): ResponseEntity<InputStreamResource> {
        logger.info("Getting sources for user");
        val httpGet = HttpGet(url)
        val response = httpClient.execute(httpGet)

        val contentLength = response.entity.contentLength
        val contentType = response.entity.contentType?.value ?: MediaType.APPLICATION_OCTET_STREAM_VALUE
        val fileName = getFileNameFromUrl(url)

        return ResponseEntity
            .ok()
            .contentLength(contentLength)
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"")
            .body(InputStreamResource(response.entity.content))
    }

    private fun getFileNameFromUrl(url: String): String {
        return url.substringAfterLast('/')
    }
}