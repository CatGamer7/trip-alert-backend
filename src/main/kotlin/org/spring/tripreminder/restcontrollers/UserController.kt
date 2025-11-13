package org.spring.tripreminder.restcontrollers

import org.spring.tripreminder.dtos.CreateUserDTO
import org.spring.tripreminder.dtos.ResponseUserDTO
import org.spring.tripreminder.mappers.UserMapper
import org.spring.tripreminder.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import jakarta.validation.Valid
import org.spring.tripreminder.dtos.UpdateUserDTO
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api")
class UserController(
    private val repository : UserRepository,
    private val mapper : UserMapper
) {

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    fun index(): List<ResponseUserDTO> =
        repository.findAll()
            .map { mapper.toResponseDto(it) }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody userData: CreateUserDTO): ResponseUserDTO {
        val user = mapper.toEntity(userData)
        repository.save(user)
        return mapper.toResponseDto(user)
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun show(@PathVariable("id") id: Long): ResponseUserDTO {
        val user = repository.findById(id)
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        return mapper.toResponseDto(user)
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody userData: UpdateUserDTO): ResponseUserDTO {
        val user = repository.findById(id)
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        mapper.updateEntity(user, userData)
        repository.save(user)
        return mapper.toResponseDto(user)
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Long) {
        if(!repository.existsById(id)){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
        repository.deleteById(id)
    }
}
