package org.spring.tripreminder.serivices

import org.spring.tripreminder.exceptions.TripNotFoundException
import org.spring.tripreminder.exceptions.UserNotFoundException
import org.spring.tripreminder.dtos.CreateTripDTO
import org.spring.tripreminder.dtos.TripResponseDTO
import org.spring.tripreminder.dtos.UpdateTripDTO
import org.spring.tripreminder.mappers.TripMapper
import org.spring.tripreminder.orms.Trip
import org.spring.tripreminder.orms.User
import org.spring.tripreminder.repositories.TripRepository
import org.spring.tripreminder.repositories.UserRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class TripService(
    private val repository: TripRepository,
    private val mapper: TripMapper,
    private val userRepository: UserRepository,
    private val reminderService: ReminderService
) {

    private fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.name
        return userRepository.getByUsername(username)
            ?: throw UserNotFoundException("Current user not found")
    }

    fun getAll(): List<TripResponseDTO> {
        val currentUser = getCurrentUser()
        return repository.findAllByUserUsername(currentUser.username)
            .map { mapper.toResponseDto(it) }
    }

    fun create(tripData: CreateTripDTO): TripResponseDTO {
        val currentUser = getCurrentUser()

        val trip = mapper.toEntity(tripData, currentUser)
        val savedTrip = repository.save(trip)

        reminderService.create(tripData.reminderData, savedTrip.id)
        return mapper.toResponseDto(savedTrip)
    }

    fun show(id: Long): TripResponseDTO {
        val trip = getTripIfOwnerOrThrow(id)
        return mapper.toResponseDto(trip)
    }

    fun update(id: Long, tripData: UpdateTripDTO): TripResponseDTO {
        val trip = getTripIfOwnerOrThrow(id)

        mapper.updateEntity(trip, tripData)
        repository.save(trip)
        return mapper.toResponseDto(trip)
    }

    fun delete(id: Long) {
        val trip = getTripIfOwnerOrThrow(id) // Проверка владения
        repository.delete(trip)
    }

    private fun getTripIfOwnerOrThrow(tripId: Long): Trip {
        val trip = repository.findById(tripId)
            .orElseThrow { TripNotFoundException("Trip $tripId not found") }

        val currentUser = getCurrentUser()

        if (trip.user.id != currentUser.id) {
            throw AccessDeniedException("You are not the owner of this trip")
        }

        return trip
    }

    fun getTripsByUser(userId: Long): MutableSet<Trip> {
        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException("User $userId not found") }
        user.trips.size
        return user.trips
    }
}
