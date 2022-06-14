package com.restwithspringbootandkotlin.service

import com.restwithspringbootandkotlin.config.FileStorageConfig
import com.restwithspringbootandkotlin.exceptions.FileStorageException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileStorageService @Autowired constructor(fileStorageConfig: FileStorageConfig){

    private lateinit var filesStorageLocation: Path

    init {
        filesStorageLocation = Paths.get(fileStorageConfig.uploadDir).toAbsolutePath().normalize()
        try {
            Files.createDirectories(filesStorageLocation);
        }catch (e: java.lang.Exception){
            throw FileStorageException("could not create the directory wher the upload files will be stored")
        }

    }

    fun storeFile(file: MultipartFile): String {

        val fileName = StringUtils.cleanPath(file.originalFilename!!);
        return try {
            if(fileName.contains(".."))
                throw FileStorageException("Sorry! Filename contains invalid path sequence")
            val targetLocation = filesStorageLocation.resolve(fileName) // caso queira jogar na nuvem etc, unica coisa que muda eh linha 38 e 39
            Files.copy(file.inputStream,targetLocation,StandardCopyOption.REPLACE_EXISTING)
            fileName
        }catch (e: java.lang.Exception){
            throw FileStorageException("could not create the directory wher the upload files will be stored")
        }
    }

    fun loadFileAsResource(fileName: String): Resource {

        return try {
            val filePath = filesStorageLocation.resolve(fileName).normalize() // caso queira jogar na nuvem etc, unica coisa que muda eh linha 49 e 50
            val resource: Resource = UrlResource(filePath.toUri());
            if(resource.exists()) resource
            else throw throw FileStorageException("file not found, please try again")
        }catch (e:Exception){
            throw FileStorageException("file not found, please try again")
        }

    }
}