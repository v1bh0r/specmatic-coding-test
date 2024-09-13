package com.store

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.store", "org.openapitools", "org.openapitools.api", "org.openapitools.model"])
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}