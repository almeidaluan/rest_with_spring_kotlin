package com.restwithspringbootandkotlin.service

import com.restwithspringbootandkotlin.exceptions.ResourceNotFoundException
import com.restwithspringbootandkotlin.model.Person
import com.restwithspringbootandkotlin.model.dto.PersonDTO
import com.restwithspringbootandkotlin.repository.PersonRepository
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service

@Service
class PersonService (personRepository: PersonRepository){

    private lateinit var personRepository: PersonRepository;

    init {
        this.personRepository = personRepository;
    }
    fun findByIdPerson(id : Long): Person {
        return personRepository.findById(id).orElseThrow { throw ResourceNotFoundException("Resource not found") };
    }

    fun findAll(): List<Person>{
        return personRepository.findAll();
    }

    fun remove(id: Long): Unit {
        personRepository.deleteById(id);
    }

    fun save(personDTO: PersonDTO): Unit{

        val person = Person()
        BeanUtils.copyProperties(personDTO, person)
        personRepository.save(person)
    }

    fun update(id: Long, personDTO: PersonDTO) {
        var person = findByIdPerson(id);
        BeanUtils.copyProperties(personDTO,person)
        personRepository.save(person)

    }
}