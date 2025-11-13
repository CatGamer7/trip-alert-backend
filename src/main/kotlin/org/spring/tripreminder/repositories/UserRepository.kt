package org.spring.tripreminder.repositories

import org.spring.tripreminder.orms.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User,Long>