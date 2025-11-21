package org.spring.tripreminder.mappers

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy
import org.spring.tripreminder.dtos.*
import org.spring.tripreminder.orms.User


@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring"
)
interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trips", ignore = true)
    fun toEntity(dto: CreateUserDTO): User

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trips", ignore = true)
    fun updateEntity(@MappingTarget user: User, dto: UpdateUserDTO)

    fun toResponseDto(user: User): ResponseUserDTO
}