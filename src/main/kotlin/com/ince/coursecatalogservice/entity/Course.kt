package com.ince.coursecatalogservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "courses")
data class Course(
    @Id
    val id: Int?,
    val name: String,
    val category: String
) {
}