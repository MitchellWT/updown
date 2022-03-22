package com.mitchelltsutsulis.updown

import com.mitchelltsutsulis.updown.config.ApiConfig
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException

@RestController
class API(val updownHandler: UpdownHandler, val apiConfig: ApiConfig) {
    @PostMapping("/upload")
    fun upload(@RequestParam("file") uploadFile: MultipartFile): ResponseEntity<String> {
        val serverDir = File(apiConfig.fileStoragePath)
        val serverFile = File(serverDir.absolutePath + "/" + uploadFile.originalFilename)
        if (!serverDir.exists() && !serverDir.mkdirs())
            return ResponseEntity("Server is unable to create upload dir!", HttpStatus.INTERNAL_SERVER_ERROR)

        val created = try { serverFile.createNewFile() } catch (ioe: IOException) { false }
        if (!created || !serverFile.canWrite())
            return ResponseEntity("Server is unable to create file!", HttpStatus.INTERNAL_SERVER_ERROR)

        return updownHandler.uploadFile(serverFile, uploadFile)
    }

    @GetMapping("/download")
    fun download(@RequestParam("filename") filename: String): Any {
        val serverFile = File(apiConfig.fileStoragePath + filename)
        if (!serverFile.exists()) return ResponseEntity("Download file not found!", HttpStatus.NOT_FOUND)

        return updownHandler.downloadFile(serverFile)
    }
}