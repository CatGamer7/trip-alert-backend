package org.spring.tripreminder.dtos

data class OsrmResponse(
    val code: String,
    val routes: List<OsrmRoute>?
)

data class OsrmRoute(
    val duration: Double,
    val distance: Double,
    val geometry: String?
)