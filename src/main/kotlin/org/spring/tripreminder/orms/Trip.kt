package org.spring.tripreminder.orms

import jakarta.persistence.*
import org.locationtech.jts.geom.Point
import org.spring.tripreminder.TransportType
import java.time.LocalDateTime

@Entity
@Table(
    name = "trips",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["name", "user_id"], name = "uk_user_trip_name")
    ]
)
class Trip(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(nullable = false)
    var name: String = "",

    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    var origin: Point? = null, // точка отправление

    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    var destination: Point? = null, // конечная точка маршрута

    @Column(name = "planned_time", nullable = false)
    var plannedTime: LocalDateTime? = null, // время выхода/выезда

    @Column(name = "arrival_time", nullable = false)
    var arrivalTime: LocalDateTime? = null, // время прибытия

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false)
    var transportType: TransportType? = null
) {
    @OneToOne(
        mappedBy = "trip",
        cascade = [CascadeType.ALL]
    )
    var reminder: Reminder? = null // связь с напоминанием
}
