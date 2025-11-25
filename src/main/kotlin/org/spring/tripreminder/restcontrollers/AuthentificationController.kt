package org.spring.tripreminder.restcontrollers

import org.spring.tripreminder.JWTUtils
import org.spring.tripreminder.dtos.AuthRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api")
class AuthentificationController(
    private val jwtUtils: JWTUtils,
    private val authentificationManager: AuthenticationManager
) {
    @PostMapping("/login")
    fun create(@RequestBody authRequest: AuthRequest): String {
        val authentification = UsernamePasswordAuthenticationToken(
            authRequest.getUsername(), authRequest.getPassword())
        authentificationManager.authenticate(authentification)

        val token = jwtUtils.generateToken(authRequest.getUsername())
        return token
    }
}
