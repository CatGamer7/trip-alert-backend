package org.spring.tripreminder.restcontrollers

import jakarta.validation.Valid
import org.spring.tripreminder.dtos.CreateReminderDTO
import org.spring.tripreminder.dtos.ReminderResponseDTO
import org.spring.tripreminder.dtos.UpdateReminderDTO
import org.spring.tripreminder.serivices.ReminderService
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

@RestController
@RequestMapping("/api/trips/{tripId}/reminders")
class ReminderController(
    private val service: ReminderService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody reminderData: CreateReminderDTO,
        @PathVariable("tripId") tripId: Long
    ): ReminderResponseDTO = service.create(reminderData, tripId)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun show(@PathVariable("id") id: Long,
             @PathVariable("tripId") tripId: Long
    ): ReminderResponseDTO = service.show(id, tripId)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("id") id: Long,
        @PathVariable("tripId") tripId: Long,
        @Valid @RequestBody reminderData: UpdateReminderDTO,
    ): ReminderResponseDTO = service.update(id, tripId, reminderData)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable("id") id: Long,
        @PathVariable("tripId") tripId: Long): Unit = service.delete(id, tripId)
}
