package org.spring.tripreminder.repositories

import org.spring.tripreminder.orms.Reminder
import org.springframework.data.jpa.repository.JpaRepository

interface ReminderRepository: JpaRepository<Reminder, Long>