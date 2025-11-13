package org.spring.tripreminder.restcontrollers

import jakarta.validation.Valid
import org.spring.tripreminder.dtos.CreateTripDTO
import org.spring.tripreminder.dtos.TripResponseDTO
import org.spring.tripreminder.dtos.UpdateTripDTO
import org.spring.tripreminder.mappers.TripMapper
import org.spring.tripreminder.repositories.TripRepository
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
class TripController(
    private val repository: TripRepository,
    private val mapper: TripMapper
) {

    @GetMapping("/trips")
    @ResponseStatus(HttpStatus.OK)
    fun index(): List<TripResponseDTO> =
        repository.findAll()
            .map { mapper.toResponseDto(it) }

    @PostMapping("/trips")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody tripData: CreateTripDTO): TripResponseDTO {
        val trip = mapper.toEntity(tripData)
        repository.save(trip)
        return mapper.toResponseDto(trip)
    }

    @GetMapping("/trips/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun show(@PathVariable("id") id: Long): TripResponseDTO {
        val trip = repository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found") }
        return mapper.toResponseDto(trip)
    }

    @PutMapping("/trips/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody tripData: UpdateTripDTO): TripResponseDTO {
        val trip = repository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found") }
        mapper.updateEntity(trip, tripData)
        repository.save(trip)
        return mapper.toResponseDto(trip)
    }

    @DeleteMapping("/trips/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Long) {
        if(!repository.existsById(id)){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found")
        }
        repository.deleteById(id)
    }
}
