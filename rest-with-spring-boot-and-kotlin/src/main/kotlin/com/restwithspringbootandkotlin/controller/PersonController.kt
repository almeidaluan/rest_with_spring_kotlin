package com.restwithspringbootandkotlin.controller

import com.restwithspringbootandkotlin.model.Person
import com.restwithspringbootandkotlin.model.dto.PersonDTO
import com.restwithspringbootandkotlin.service.PersonService

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("/persons")
@Tag(name = "People", description = "Endpoint for managing People")
@CrossOrigin
class PersonController (personService: PersonService){

    private lateinit var personService: PersonService

    init {
        this.personService = personService
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Person", description = "Finds a Person")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "find one user", content = [Content(schema = Schema(implementation = Person::class))]),
        ApiResponse(responseCode = "400", description = "bad request", content = [Content()]),
        ApiResponse(responseCode = "404", description = "not found", content = [Content()])]
    )
    fun findByIdPerson(@PathVariable id: Long) : ResponseEntity<Person> {
        return ResponseEntity.ok().body(personService.findByIdPerson(id))
    }

    @GetMapping()
    @Operation(summary = "Finds All Person", description = "Finds All Person")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "...", content = [
            (Content(mediaType = "application/json", array = (
                    ArraySchema(schema = Schema(implementation = Person::class)))))]),
        ApiResponse(responseCode = "400", description = "...", content = [Content()]),
        ApiResponse(responseCode = "404", description = "...", content = [Content()])]
    )
    fun findAll(): ResponseEntity<List<Person>>{
        return ResponseEntity.ok().body(personService.findAll())
    }


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit>{
        personService.remove(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build<Unit>()
    }

    @PostMapping()
    fun create(@RequestBody personDTO: PersonDTO): ResponseEntity<Unit>{
        personService.save(personDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build<Unit>()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody personDTO: PersonDTO): ResponseEntity<Unit>{
        personService.update(id,personDTO)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build<Unit>()
    }

}