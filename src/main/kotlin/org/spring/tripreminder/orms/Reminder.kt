package org.spring.tripreminder.orms

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "reminders")
class Reminder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    var trip: Trip,

    @Column(name = "notification_time", nullable = false)
    val notificationTime: LocalDateTime,

    @Column(name = "sent")
    val sent: Boolean = false
)
