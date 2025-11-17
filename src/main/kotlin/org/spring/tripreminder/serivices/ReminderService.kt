package org.spring.tripreminder.serivices

import exceptions.ReminderNotFoundException
import exceptions.TripNotFoundException
import org.spring.tripreminder.dtos.CreateReminderDTO
import org.spring.tripreminder.dtos.ReminderResponseDTO
import org.spring.tripreminder.dtos.UpdateReminderDTO
import org.spring.tripreminder.mappers.ReminderMapper
import org.spring.tripreminder.repositories.ReminderRepository
import org.spring.tripreminder.repositories.TripRepository
import org.springframework.stereotype.Service

@Service
class ReminderService(
    private val repository: ReminderRepository,
    private val mapper: ReminderMapper,
    private val tripRepository: TripRepository
) {

    fun create(reminderData: CreateReminderDTO, tripId: Long): ReminderResponseDTO {
        val trip = tripRepository.findById(tripId)
            .orElseThrow { TripNotFoundException("Trip $tripId not found") }
        val reminder = mapper.toEntity(reminderData, trip)
        val savedReminder = repository.save(reminder)
        return mapper.toResponseDto(savedReminder)
    }

    fun show(id: Long, tripId: Long): ReminderResponseDTO {
        val reminder = repository.findByIdAndTripId(id, tripId)
            .orElseThrow { ReminderNotFoundException( "Reminder $id not found") }
        return mapper.toResponseDto(reminder)
    }

    fun update(id: Long, tripId: Long, reminderData: UpdateReminderDTO): ReminderResponseDTO {
        val reminder = repository.findByIdAndTripId(id, tripId)
            .orElseThrow { ReminderNotFoundException( "Reminder $id not found") }
        mapper.updateEntity(reminder, reminderData)
        repository.save(reminder)
        return mapper.toResponseDto(reminder)
    }

    fun delete(id: Long, tripId: Long) {
        val reminder = repository.findByIdAndTripId(id,tripId)
            .orElseThrow { ReminderNotFoundException( "Reminder $id not found") }
        repository.delete(reminder)
    }
}
