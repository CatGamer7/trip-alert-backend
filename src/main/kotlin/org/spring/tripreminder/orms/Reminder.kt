package org.spring.tripreminder.orms

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "reminders")
class Reminder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "notification_time", nullable = false)
    var notificationTime: LocalDateTime, // время, когда необходимо отпарвить напоминаение

    @Column(name = "sent")
    var sent: Boolean = false // отправлено или нет
){
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", unique = true, nullable = false)
    lateinit var trip: Trip
}
