package org.spring.tripreminder.restcontrollers

import jakarta.validation.Valid
import org.spring.tripreminder.dtos.CreateReminderDTO
import org.spring.tripreminder.dtos.ReminderResponseDTO
import org.spring.tripreminder.dtos.UpdateReminderDTO
import org.spring.tripreminder.mappers.ReminderMapper
import org.spring.tripreminder.repositories.ReminderRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api")
class ReminderController(
    private val repository: ReminderRepository,
    private val mapper: ReminderMapper
) {

    @GetMapping("/reminders")
    @ResponseStatus(HttpStatus.OK)
    fun index(): List<ReminderResponseDTO> =
        repository.findAll()
            .map { mapper.toResponseDto(it) }

    @PostMapping("/reminders")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody reminderData: CreateReminderDTO): ReminderResponseDTO {
        val reminder = mapper.toEntity(reminderData)
        repository.save(reminder)
        return mapper.toResponseDto(reminder)
    }

    @GetMapping("/reminders/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun show(@PathVariable("id") id: Long): ReminderResponseDTO {
        val reminder = repository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Reminder not found") }
        return mapper.toResponseDto(reminder)
    }

    @PutMapping("/reminders/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody reminderData: UpdateReminderDTO): ReminderResponseDTO {
        val reminder = repository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Reminder not found") }
        mapper.updateEntity(reminder, reminderData)
        repository.save(reminder)
        return mapper.toResponseDto(reminder)
    }

    @DeleteMapping("/reminders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Long) {
        if(!repository.existsById(id)){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Reminder not found")
        }
        repository.deleteById(id)
    }
}
