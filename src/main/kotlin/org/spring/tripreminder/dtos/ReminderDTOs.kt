package org.spring.tripreminder.dtos

import java.time.LocalDateTime

data class CreateReminderDTO(
    val tripId: Long,
    val notificationTime: LocalDateTime
)

data class UpdateReminderDTO(
    val notificationTime: LocalDateTime? = null,
    val sent: Boolean? = false
)

data class ReminderResponseDTO(
    val id: Long,
    val tripId: Long,
    val notificationTime: LocalDateTime,
    val sent: Boolean
)