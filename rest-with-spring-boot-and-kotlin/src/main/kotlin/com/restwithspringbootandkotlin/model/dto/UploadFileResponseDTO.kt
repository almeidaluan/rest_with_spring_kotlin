package com.restwithspringbootandkotlin.model.dto

class UploadFileResponseDTO (

    var fileName: String = "",
    var fileDownloadUri: String = "",
    var fileType: String = "",
    var fileSize: Long = 0
)