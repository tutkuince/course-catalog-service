package com.ince.coursecatalogservice.repository

import com.ince.coursecatalogservice.entity.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CourseRepository: JpaRepository<Course, Int> {

    // SELECT * FROM Course WHERE NAME LIKE :name
    fun findCourseByNameContaining(name: String): List<Course>

    @Query(value = "SELECT * FROM Courses WHERE Name LIKE %?1%", nativeQuery = true)
    fun findCoursesByName(name: String): List<Course>
}