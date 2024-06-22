package com.ince.coursecatalogservice.dto

import jakarta.validation.constraints.NotBlank

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "Name must not be blank")
    val name: String,
    @get:NotBlank(message = "Category must not be blank")
    val category: String
) {
}