package com.ince.coursecatalogservice.repository

import com.ince.coursecatalogservice.entity.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository: JpaRepository<Course, Int> {

    // SELECT * FROM Course WHERE NAME LIKE :name
    fun findCourseByNameContaining(name: String): List<Course>
}