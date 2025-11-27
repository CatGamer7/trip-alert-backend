package org.spring.tripreminder.serivices

import exceptions.TripNotFoundException
import exceptions.UserNotFoundException
import org.spring.tripreminder.dtos.CreateTripDTO
import org.spring.tripreminder.dtos.TripResponseDTO
import org.spring.tripreminder.dtos.UpdateTripDTO
import org.spring.tripreminder.mappers.TripMapper
import org.spring.tripreminder.orms.Trip
import org.spring.tripreminder.repositories.TripRepository
import org.spring.tripreminder.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class TripService(
    private val repository: TripRepository,
    private val mapper: TripMapper,
    private val userRepository: UserRepository,
    private val reminderService: ReminderService
) {

    fun getAll(): List<TripResponseDTO> =
        repository.findAll()
            .map{ mapper.toResponseDto(it) }

    fun create(tripData: CreateTripDTO): TripResponseDTO {
        val user = userRepository.findById(tripData.userId)
            .orElseThrow{ UserNotFoundException( "User ${tripData.userId} not found") }

        println(tripData.arrivalTime)

        val trip = mapper.toEntity(tripData, user)
        val savedTrip = repository.save(trip)

        reminderService.create(tripData.reminderData, savedTrip.id)
        return mapper.toResponseDto(savedTrip)
    }

    fun show(id: Long): TripResponseDTO {
        val trip = repository.findById(id)
            .orElseThrow { TripNotFoundException("Trip $id not found") }
        return mapper.toResponseDto(trip)
    }

    fun update(id: Long, tripData: UpdateTripDTO): TripResponseDTO {
        val trip = repository.findById(id)
            .orElseThrow { TripNotFoundException("Trip $id not found") }

        mapper.updateEntity(trip, tripData)
        repository.save(trip)

        // добавить перерасчет напоминания

        return mapper.toResponseDto(trip)
    }

    fun delete(id: Long) {
        val trip = repository.findById(id)
            .orElseThrow { TripNotFoundException("Trip $id not found") }

        repository.delete(trip)
    }

    fun getTripsByUser(userId: Long): MutableSet<Trip> {
        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException("User $userId not found") }
        user.trips.size
        return user.trips
    }
}
