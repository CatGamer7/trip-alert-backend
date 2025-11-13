package org.spring.tripreminder.dtos

import org.locationtech.jts.geom.Point
import org.spring.tripreminder.TransportType
import java.time.LocalDateTime

data class CreateTripDTO(
    val userId: Long,
    val name: String,
    val origin: Point,
    val destination: Point,
    val plannedTime: LocalDateTime,
    val arrivalTime: LocalDateTime,
    val transportType: TransportType,
    val alertTime: LocalDateTime
)

data class UpdateTripDTO(
    val name: String? = null,
    val origin: Point? = null,
    val destination: Point? = null,
    val plannedTime: LocalDateTime? = null,
    val arrivalTime: LocalDateTime? = null,
    val transportType: TransportType? = null,
    val alertTime: LocalDateTime? = null
)

data class TripResponseDTO(
    val id: Long,
    val userId: Long,
    val name: String,
    val origin: Point,
    val destination: Point,
    val plannedTime: LocalDateTime,
    val arrivalTime: LocalDateTime,
    val transportType: TransportType,
    val alertTime: LocalDateTime
)