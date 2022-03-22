package com.mitchelltsutsulis.updown

import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@Component
class UpdownHandler {
    fun uploadFile(serverFile: File, uploadFile: MultipartFile): ResponseEntity<String> {
        val serverFileOut = FileOutputStream(serverFile)

        serverFileOut.write(uploadFile.bytes)
        serverFileOut.close()

        return ResponseEntity("File has been uploaded!", HttpStatus.OK)
    }

    fun downloadFile(serverFile: File): ResponseEntity<InputStreamResource> {
        // REFERENCE: https://stackoverflow.com/questions/35680932/download-a-file-from-spring-boot-rest-service
        val fileResource = InputStreamResource(FileInputStream(serverFile))
        val headers = HttpHeaders()
        headers.add("Content-Length", serverFile.length().toString())
        headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM.toString())

        return ResponseEntity(fileResource, headers, HttpStatus.OK)
    }
}