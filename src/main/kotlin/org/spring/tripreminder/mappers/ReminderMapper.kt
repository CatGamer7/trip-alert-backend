package org.spring.tripreminder.mappers

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy
import org.spring.tripreminder.dtos.*
import org.spring.tripreminder.orms.Reminder
import org.spring.tripreminder.orms.Trip

@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring"
)
interface ReminderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trip", source = "trip")
    fun toEntity(dto: CreateReminderDTO, trip: Trip): Reminder

    @Mapping(target = "id", ignore = true)
    fun updateEntity(@MappingTarget reminder: Reminder, dto: UpdateReminderDTO)

    @Mapping(target = "tripId", source = "trip.id")
    fun toResponseDto(reminder: Reminder): ReminderResponseDTO
}