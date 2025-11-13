package org.spring.tripreminder.mappers

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy
import org.spring.tripreminder.dtos.*
import org.spring.tripreminder.orms.Trip

@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring"
)
interface TripMapper {
    @Mapping(target = "id", ignore = true)
    fun toEntity(dto: CreateTripDTO): Trip

    @Mapping(target = "id", ignore = true)
    fun updateEntity(@MappingTarget trip: Trip, dto: UpdateTripDTO)

    fun toResponseDto(trip: Trip): TripResponseDTO
}