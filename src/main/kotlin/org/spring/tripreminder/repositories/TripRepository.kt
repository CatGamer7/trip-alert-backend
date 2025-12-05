package org.spring.tripreminder.repositories

import org.spring.tripreminder.orms.Trip
import org.springframework.data.jpa.repository.JpaRepository

interface TripRepository: JpaRepository<Trip, Long>{
    fun findAllByUserUsername(username: String): List<Trip>
}