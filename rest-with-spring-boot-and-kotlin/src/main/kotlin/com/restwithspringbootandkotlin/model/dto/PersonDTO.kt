package com.restwithspringbootandkotlin.model.dto

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

data class PersonDTO(
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var gender: String = ""
)