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
    val origin: Point,

    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    val destination: Point,

    @Column(name = "planned_time", nullable = false)
    val plannedTime: LocalDateTime,

    @Column(name = "arrival_time", nullable = false)
    val arrivalTime: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false)
    val transportType: TransportType = TransportType.WALK,

    @Column(name = "alert_time", nullable = false)
    val alertTime: LocalDateTime
)
