package org.spring.tripreminder.serivices

import org.locationtech.jts.geom.Point
import org.spring.tripreminder.OsrmTransportType
import org.spring.tripreminder.dtos.OsrmResponse
import org.spring.tripreminder.exceptions.RouteNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Service
class OsrmRoutingService(
    private val webClient: WebClient.Builder,
    @param:Value("\${osrm.base-url}") private val baseUrl: String
) {

    private val client = webClient.baseUrl(baseUrl).build()

    fun calculateDuration(origin: Point, destination: Point, mode: OsrmTransportType): Duration {
        val coordinates = "${origin.x},${origin.y};${destination.x},${destination.y}"

        val response = client.get()
            .uri("/route/v1/${mode.osrmTransportType}/$coordinates?overview=false") // overview=false чтобы не тянуть геометрию, если нужно только время
            .retrieve()
            .bodyToMono(OsrmResponse::class.java)
            .block()
        if (response == null || response.routes.isNullOrEmpty()) {
            throw RouteNotFoundException("Route not found or OSRM Error")
        }

        return Duration.ofSeconds(response.routes[0].duration.toLong())
    }

}
