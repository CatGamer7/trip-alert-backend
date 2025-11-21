package org.spring.tripreminder.orms

import jakarta.persistence.*
import org.locationtech.jts.geom.Point
import org.spring.tripreminder.TransportType
import java.time.LocalDateTime

@Entity
@Table(name = "trips")
class Trip(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    val origin: Point, // точка отправление

    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    val destination: Point, // конечная точка маршрута

    @Column(name = "planned_time", nullable = false)
    val plannedTime: LocalDateTime, // время выхода/выезда

    @Column(name = "arrival_time", nullable = false)
    val arrivalTime: LocalDateTime, // время прибытия

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false)
    val transportType: TransportType
){
    @OneToOne(
        mappedBy = "trip",
        cascade = [CascadeType.ALL]
    )
    var reminder: Reminder? = null // связь с напоминанием
}
