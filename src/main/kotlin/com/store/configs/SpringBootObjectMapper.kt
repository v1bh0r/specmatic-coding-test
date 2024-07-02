package com.store.configs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.openapitools.model.ProductType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
open class SpringBootObjectMapper {

    @Bean
    @Primary
    open fun objectMapper(): ObjectMapper {
        val module = SimpleModule()
            .addDeserializer(ProductType::class.java, ProductTypeDeserializer())
            .addDeserializer(String::class.java, StrictStringDeserializer())

        val mapper = ObjectMapper()
        module.addDeserializer(ProductType::class.java, ProductTypeDeserializer())

        // Add your own customizations here
        mapper.registerModules(KotlinModule.Builder().build(), module)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false) // Serialize dates as ISO-8601 strings

        return mapper
    }
}
