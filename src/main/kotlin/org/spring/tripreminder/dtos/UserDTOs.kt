package org.spring.tripreminder.dtos

import org.spring.tripreminder.TransportType


data class CreateUserDTO(
    val username: String,
    val password: String,
    val timeOffset: Int = 10,
    val preferredTransport: TransportType,
)

data class UpdateUserDTO(
    val timeOffset: Int? = null,
    val preferredTransport: TransportType? = null
)

data class ResponseUserDTO(
    val id: Long,
    val username: String,
    val timeOffset: Int,
    val preferredTransport: TransportType
)
