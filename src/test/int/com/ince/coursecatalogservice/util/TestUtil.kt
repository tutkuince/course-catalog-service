package com.ince.coursecatalogservice.util

import com.ince.coursecatalogservice.entity.Course

fun courseEntityList() = listOf(
    Course(
        null,
        "Build Restful APIs using Spring Boot and Kotlin",
        "Development"
    ),
    Course(
        null,
        "Wiremock for Java Developers",
        "Development"
    ),
    Course(
        null,
        "Build Reactive Microservices using Spring WebFlux/Spring Boot",
        "Development"
    )
)