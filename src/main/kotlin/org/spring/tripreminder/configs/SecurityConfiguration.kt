package org.spring.tripreminder.configs

import org.spring.tripreminder.config.org.spring.tripreminder.serivices.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.Customizer
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val passwordEncoder: PasswordEncoder,
    private val userService: CustomUserDetailsService,
    private val jwtDecoder: JwtDecoder
) {
    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .build()
    }

    @Bean
    fun daoAuthProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userService)
        provider.setPasswordEncoder(passwordEncoder)
        return provider
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity, introspector: HandlerMappingIntrospector): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/login").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .oauth2ResourceServer { rs ->
                rs.jwt { jwt -> jwt.decoder(jwtDecoder) }
            }
            .httpBasic(Customizer.withDefaults())
            .build()
    }
}