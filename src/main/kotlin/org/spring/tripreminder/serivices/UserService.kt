package org.spring.tripreminder.serivices

import exceptions.UserNotFoundException
import org.spring.tripreminder.dtos.CreateUserDTO
import org.spring.tripreminder.dtos.ResponseUserDTO
import org.spring.tripreminder.dtos.UpdateUserDTO
import org.spring.tripreminder.mappers.UserMapper
import org.spring.tripreminder.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository,
    private val mapper: UserMapper,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAll(): List<ResponseUserDTO> =
        repository.findAll().map{ mapper.toResponseDto(it) }

    fun create(userData: CreateUserDTO): ResponseUserDTO  {
        val user = mapper.toEntity(userData)
        user.password = passwordEncoder.encode(userData.password)
        repository.save(user)
        return mapper.toResponseDto(user)
    }

    fun show(id: Long): ResponseUserDTO {
        val user = repository.findById(id)
            .orElseThrow{ UserNotFoundException("User $id not found!") }
        return mapper.toResponseDto(user)
    }

    fun update(id: Long, userData: UpdateUserDTO): ResponseUserDTO {
        val user = repository.findById(id)
        .orElseThrow{ UserNotFoundException("User $id not found!") }
        mapper.updateEntity(user,userData)
        repository.save(user)
        return mapper.toResponseDto(user)
    }

    fun delete(id: Long) {
        val user = repository.findById(id)
            .orElseThrow { UserNotFoundException("User $id not found!") }
        repository.delete(user)
    }
}
