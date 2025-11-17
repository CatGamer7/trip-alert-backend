package org.spring.tripreminder.orms

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "reminders")
class Reminder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", unique = true, nullable = false)
    var trip: Trip,

    @Column(name = "notification_time", nullable = false)
    val notificationTime: LocalDateTime, // время, когда необходимо отпарвить напоминаение

    @Column(name = "sent")
    val sent: Boolean = false // отправлено или нет
)
