package com.restwithspringbootandkotlin.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException : RuntimeException{
    constructor(exception: String): super(exception)
    constructor(exception: String,cause: Throwable) : super(exception,cause)
}