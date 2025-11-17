package org.spring.tripreminder.restcontrollers

import org.spring.tripreminder.dtos.CreateUserDTO
import org.spring.tripreminder.dtos.ResponseUserDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import jakarta.validation.Valid
import org.spring.tripreminder.dtos.UpdateUserDTO
import org.spring.tripreminder.serivices.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@RestController
@RequestMapping("/api/users")
class UserController(
    private val service: UserService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun index(): List<ResponseUserDTO> = service.getAll()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody userData: CreateUserDTO):
            ResponseUserDTO = service.create(userData)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun show(@PathVariable("id") id: Long): ResponseUserDTO = service.show(id)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody userData: UpdateUserDTO): ResponseUserDTO = service.update(id, userData)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Long): Unit = service.delete(id)
}