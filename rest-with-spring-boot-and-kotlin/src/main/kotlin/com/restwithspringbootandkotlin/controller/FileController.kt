package com.restwithspringbootandkotlin.controller

import com.restwithspringbootandkotlin.config.FileStorageConfig
import com.restwithspringbootandkotlin.model.dto.UploadFileResponseDTO
import com.restwithspringbootandkotlin.service.FileStorageService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/files")
class FileController {

    @Autowired
    private lateinit var fileStorageService: FileStorageService

    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam("file") file: MultipartFile): UploadFileResponseDTO{

        val fileName = fileStorageService.storeFile(file);

        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/files/uploadFile/")
            .path(fileName)
            .toUriString()
        return UploadFileResponseDTO(fileName,fileDownloadUri,file.contentType!!,file.size)

    }

    @PostMapping("/uploadMultipleFile")
    fun uploadMultipleFiles(@RequestParam("files") files: Array<MultipartFile>): List<UploadFileResponseDTO>{

        val uploadFilesResponseDTO = arrayListOf<UploadFileResponseDTO>()

        for (file in files){
            val uploadFileResponseDTO: UploadFileResponseDTO = uploadFile(file)
            uploadFilesResponseDTO.add(uploadFileResponseDTO)
        }
        return uploadFilesResponseDTO;
    }


    @GetMapping("/downloadFile/{fileName:.+}")
    fun downloadFile(@PathVariable("fileName") fileName: String,request: HttpServletRequest): ResponseEntity<Resource>{
        val resource = fileStorageService.loadFileAsResource(fileName);

        var contentType = ""

        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)
        }catch (e: Exception){

        }
        if(contentType.isBlank()) contentType = "application/octet-stream"

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
            """
                attachment; filename="${resource.filename}
            """)
            .contentType(MediaType.parseMediaType(contentType)).body(resource)

    }
}