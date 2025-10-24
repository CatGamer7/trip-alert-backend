package org.spring.tripreminder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TripReminderApplication

fun main(args: Array<String>) {
    runApplication<TripReminderApplication>(*args)
}
