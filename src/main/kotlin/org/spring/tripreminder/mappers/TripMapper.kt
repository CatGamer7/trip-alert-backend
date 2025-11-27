package org.spring.tripreminder.mappers

import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy
import org.spring.tripreminder.dtos.*
import org.spring.tripreminder.orms.Trip
import org.spring.tripreminder.orms.User

@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    uses = [ReminderMapper::class]
)
abstract class TripMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reminder", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "origin", source = "dto.origin")
    @Mapping(target = "destination", source = "dto.destination")
    @Mapping(target = "plannedTime", source = "dto.plannedTime")
    @Mapping(target = "arrivalTime", source = "dto.arrivalTime")
    @Mapping(target = "transportType", source = "dto.transportType")
    abstract fun toEntity(dto: CreateTripDTO, user: User): Trip

    @AfterMapping
    fun linkReminder(@MappingTarget trip: Trip) {
        trip.reminder?.trip = trip
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "reminder", source = "reminderData")
    abstract fun updateEntity(@MappingTarget trip: Trip, dto: UpdateTripDTO)

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "reminder", source = "reminder")
    abstract fun toResponseDto(trip: Trip): TripResponseDTO
}