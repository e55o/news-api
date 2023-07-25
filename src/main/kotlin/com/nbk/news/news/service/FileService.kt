package com.nbk.news.news.service

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.slf4j.LoggerFactory
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class FileService(private val httpClient: CloseableHttpClient) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getFile(url: String): ResponseEntity<ByteArrayResource> {
        logger.info("Getting sources for user");
        val fileData = downloadFile(url)
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_OCTET_STREAM
            contentDisposition = ContentDisposition.builder("attachment").filename(getFileNameFromUrl(url)).build()
            contentLength = fileData.size.toLong()
        }
        return ResponseEntity.ok().headers(headers).body(ByteArrayResource(fileData))
    }


    private fun downloadFile(url: String): ByteArray {
        val httpGet = HttpGet(url)
        val response = httpClient.execute(httpGet)

        val outputStream = ByteArrayOutputStream()
        response.entity.content.copyTo(outputStream)
        response.close()

        return outputStream.toByteArray()
    }

    private fun getFileNameFromUrl(url: String): String {
        return url.substringAfterLast('/')
    }
}