package com.restwithspringbootandkotlin.repository

import com.restwithspringbootandkotlin.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<Person,Long> {
}