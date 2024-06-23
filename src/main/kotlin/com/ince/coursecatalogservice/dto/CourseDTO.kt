package com.ince.coursecatalogservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "Name must not be blank")
    val name: String,
    @get:NotBlank(message = "Category must not be blank")
    val category: String,
    @get:NotNull(message = "InstructorId must not be null")
    val instructorId: Int? = null
) {
}