package org.spring.tripreminder.repositories

import org.spring.tripreminder.orms.Reminder
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ReminderRepository: JpaRepository<Reminder, Long>{
    fun findByIdAndTripId(id: Long, tripId: Long): Optional<Reminder>
}