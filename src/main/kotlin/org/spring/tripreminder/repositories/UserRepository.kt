package org.spring.tripreminder.repositories

import org.spring.tripreminder.orms.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import java.util.Optional

interface UserRepository: JpaRepository<User,Long>{

    fun findByUsername(username: String?): Optional<UserDetails>
    fun getByUsername(username: String?): User?
}