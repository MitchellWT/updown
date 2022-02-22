package com.mitchelltsutsulis.updown.handler

import com.mitchelltsutsulis.updown.ResponseBody
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream

@Component
class UpdownHandler {
    fun uploadFile(serverFile: File, uploadFile: MultipartFile): ResponseEntity<ResponseBody> {
        val serverFileOut = FileOutputStream(serverFile)
        val resBody = ResponseBody("File has been uploaded!")

        serverFileOut.write(uploadFile.bytes)
        serverFileOut.close()

        return ResponseEntity(resBody, HttpStatus.OK)
    }
}