package com.ince.coursecatalogservice.util

import com.ince.coursecatalogservice.dto.CourseDTO
import com.ince.coursecatalogservice.dto.InstructorDTO
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

fun courseDTO(
    id: Int? = null,
    name: String = "Build Restful APIs using Spring Boot and Kotlin",
    category: String = "Development"
) = CourseDTO(
    id,
    name,
    category
)

fun instructorDTO(
    id: Int? = null,
    name: String = "Robert Cecil Martin",
) = InstructorDTO(
    id,
    name
)