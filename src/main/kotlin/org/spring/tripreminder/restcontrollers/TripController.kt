package org.spring.tripreminder.restcontrollers

import jakarta.validation.Valid
import org.spring.tripreminder.dtos.CreateReminderDTO
import org.spring.tripreminder.dtos.CreateTripDTO
import org.spring.tripreminder.dtos.TripResponseDTO
import org.spring.tripreminder.dtos.UpdateTripDTO
import org.spring.tripreminder.serivices.TripService
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
@RequestMapping("/api/trips")
class TripController(
    private val service: TripService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun index(): List<TripResponseDTO> = service.getAll()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody tripData: CreateTripDTO,
    ): TripResponseDTO = service.create(tripData)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun show(@PathVariable("id") id: Long): TripResponseDTO = service.show(id)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody tripData: UpdateTripDTO): TripResponseDTO = service.update(id, tripData)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Long): Unit = service.delete(id)
}
