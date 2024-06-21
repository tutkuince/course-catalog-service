package com.ince.coursecatalogservice.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    @get:NotBlank(message = "Name must not be blank")
    var name: String,
    @get:NotBlank(message = "Category must not be blank")
    var category: String
) {
}