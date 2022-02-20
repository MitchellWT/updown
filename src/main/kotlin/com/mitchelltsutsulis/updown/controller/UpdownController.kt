package com.mitchelltsutsulis.updown.controller

import com.mitchelltsutsulis.updown.config.ApiConfig
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files

@RestController
class UpdownController(val apiConfig: ApiConfig) {
    @PostMapping("/upload")
    fun upload(@RequestParam("file") uploadFile: MultipartFile): String {
        val serverDirectory = File(apiConfig.fileStoragePath)
        val serverFile = File(apiConfig.fileStoragePath + uploadFile.originalFilename)
        if (!serverDirectory.exists() && !serverDirectory.mkdirs()) {
            // TODO: replace with proper response object
            return "ERROR DO NOT HAVE PERMISSION TO WRITE TO DIR!"
        }
        if (!serverFile.createNewFile() || !serverFile.canWrite()) {
            // TODO: replace with proper response object
            return "ERROR UNABLE TO CREATE FILE!"
        }

        val fileOut = FileOutputStream(serverFile)
        fileOut.write(uploadFile.bytes)
        fileOut.close()

        // TODO: replace with proper response object
        return "FILE UPLOADED!"
    }

    @GetMapping("/download")
    fun download(@RequestParam("filename") filename: String): ResponseEntity<Any> {
        val serverFile = File(apiConfig.fileStoragePath + filename)
        if (!serverFile.exists()) {
            // TODO: provide more information in response
            return ResponseEntity.notFound()
                .build()
        }

        // REFERENCE: https://stackoverflow.com/questions/35680932/download-a-file-from-spring-boot-rest-service
        val fileResource = InputStreamResource(FileInputStream(serverFile))
        return ResponseEntity.ok()
            .contentLength(serverFile.length())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(fileResource)
    }
}