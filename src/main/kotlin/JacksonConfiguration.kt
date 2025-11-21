package org.spring.tripreminder.config

import com.fasterxml.jackson.databind.Module
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.n52.jackson.datatype.jts.JtsModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfiguration {

    @Bean
    fun geometryFactory(): GeometryFactory {
        return GeometryFactory(PrecisionModel(), 4326)
    }

    @Bean
    fun jtsModule(geometryFactory: GeometryFactory): Module {
        return JtsModule(geometryFactory)
    }
}