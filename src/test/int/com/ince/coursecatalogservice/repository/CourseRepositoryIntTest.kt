package com.ince.coursecatalogservice.repository

import com.ince.coursecatalogservice.util.PostgresSQLContainerInitializer
import com.ince.coursecatalogservice.util.courseEntityList
import com.ince.coursecatalogservice.util.instructorEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream
import kotlin.test.assertEquals

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryIntTest: PostgresSQLContainerInitializer() {

    @Autowired
    lateinit var courseRepository: CourseRepository
    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        instructorRepository.deleteAll()
        val instructorEntity = instructorEntity()
        instructorRepository.save(instructorEntity)
        courseRepository.saveAll(courseEntityList(instructorEntity))
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

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursesByName_secondApproach(name: String, expectedSize: Int) {
        val courses = courseRepository.findCoursesByName(name)
        println("Courses: $courses")
        assertEquals(expectedSize, courses.size)
    }

    companion object {
        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(Arguments.arguments("Spring Boot", 2), Arguments.arguments("Wiremock", 1))
        }
    }
}