package org.spring.tripreminder

import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
class JWTUtils(
    private val encoder: JwtEncoder
) {
    fun generateToken(username: String): String {
        val now: Instant = Instant.now()
        val claims: JwtClaimsSet? = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .subject(username)
            .build()
        return this.encoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}
