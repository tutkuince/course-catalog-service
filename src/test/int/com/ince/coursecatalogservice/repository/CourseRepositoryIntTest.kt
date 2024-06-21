package com.ince.coursecatalogservice.repository

import com.ince.coursecatalogservice.util.courseEntityList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntTest {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        courseRepository.saveAll(courseEntityList())
    }

    @Test
    fun findCourseByNameContaining() {
        val courses = courseRepository.findCourseByNameContaining("Spring Boot")
        println("Courses: $courses")
        assertEquals(2, courses.size)
    }

    @Test
    fun findCoursesByName() {
        val courses = courseRepository.findCoursesByName("Spring Boot")
        println("Courses: $courses")
        assertEquals(2, courses.size)
    }
}