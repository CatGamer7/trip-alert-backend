package org.spring.tripreminder.orms

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.spring.tripreminder.TransportType

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    val id: Long = 0,

    @Column(name = "username", nullable = false, unique = true)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "time_offset", nullable = false)
    val timeOffset: Int = 15,

    @Column(name = "preferred_transport", nullable = false)
    val preferredTransport: TransportType = TransportType.WALK
)