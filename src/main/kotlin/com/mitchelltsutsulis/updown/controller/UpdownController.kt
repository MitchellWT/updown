package com.mitchelltsutsulis.updown.controller

import com.mitchelltsutsulis.updown.ResponseBody
import com.mitchelltsutsulis.updown.config.ApiConfig
import com.mitchelltsutsulis.updown.handler.UpdownHandler
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
class UpdownController(val updownHandler: UpdownHandler, val apiConfig: ApiConfig) {
    @PostMapping("/upload")
    fun upload(@RequestParam("file") uploadFile: MultipartFile): ResponseEntity<ResponseBody> {
        val serverDir = File(apiConfig.fileStoragePath)
        val serverFile = File(serverDir.absolutePath + "/" + uploadFile.originalFilename)
        if (!serverDir.exists() && !serverDir.mkdirs()) {
            val resBody = ResponseBody("Server is unable to create upload dir!")
            return ResponseEntity(resBody, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        val created = try { serverFile.createNewFile() } catch (ioe: IOException) { false }
        if (!created || !serverFile.canWrite()) {
            val resBody = ResponseBody("Server is unable to create file!")
            return ResponseEntity(resBody, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return updownHandler.uploadFile(serverFile, uploadFile)
    }

    @GetMapping("/download")
    fun download(@RequestParam("filename") filename: String): Any {
        val serverFile = File(apiConfig.fileStoragePath + filename)
        if (!serverFile.exists()) {
            val resBody = ResponseBody("Download file not found!")
            return ResponseEntity<ResponseBody>(resBody, HttpStatus.NOT_FOUND)
        }

        return updownHandler.downloadFile(serverFile)
    }
}