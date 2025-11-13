package org.spring.tripreminder

import net.datafaker.Faker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TestConfiguration {
    @Bean
    fun faker(): Faker {
        return Faker()
    }
}
