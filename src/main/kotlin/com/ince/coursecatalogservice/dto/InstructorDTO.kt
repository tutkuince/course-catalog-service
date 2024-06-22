package com.ince.coursecatalogservice.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDTO(
    val id: Int?,
    @get:NotBlank(message = "Name must not be blank")
    var name: String
) {
}