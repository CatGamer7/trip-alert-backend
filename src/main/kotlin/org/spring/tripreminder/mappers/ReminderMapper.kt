package org.spring.tripreminder.mappers

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy
import org.spring.tripreminder.dtos.*
import org.spring.tripreminder.orms.Reminder

@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring"
)
interface ReminderMapper {
    @Mapping(target = "id", ignore = true)
    fun toEntity(dto: CreateReminderDTO): Reminder

    @Mapping(target = "id", ignore = true)
    fun updateEntity(@MappingTarget reminder: Reminder, dto: UpdateReminderDTO)

    fun toResponseDto(reminder: Reminder): ReminderResponseDTO
}