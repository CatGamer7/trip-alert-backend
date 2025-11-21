package org.spring.tripreminder.config.org.spring.tripreminder.serivices

import org.spring.tripreminder.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User $username not found") }
        return user
    }
}
