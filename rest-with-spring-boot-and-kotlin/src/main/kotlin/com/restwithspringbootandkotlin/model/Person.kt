package com.restwithspringbootandkotlin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.NoArgsConstructor

@Entity
@Table(name = "person")
data class Person (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var firstName: String = "",
        var lastName: String = "",
        var address: String = "",
        var gender: String = ""
        )