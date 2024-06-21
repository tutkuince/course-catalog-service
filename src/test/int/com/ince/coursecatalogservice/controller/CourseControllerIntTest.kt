package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.dto.CourseDTO
import com.ince.coursecatalogservice.entity.Course
import com.ince.coursecatalogservice.repository.CourseRepository
import com.ince.coursecatalogservice.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        courseRepository.saveAll(courseEntityList())
    }

    @Test
    fun addCourseTest() {
        val courseDTO = CourseDTO(null, "Test Name", "Test Category")
        val result = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Test Name", courseDTO.name, "Name should be same")
        assertEquals("Test Category", courseDTO.category, "Category Name should be same")
        // assertNotNull(result?.id, "Result cannot be null")
        Assertions.assertTrue{ result!!.id != null }
    }

    @Test
    fun retrieveAllCoursesTest() {
        val courseDTOList = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody


        assertEquals(3, courseDTOList!!.size, "Size should be same")
    }

    @Test
    fun updateCourseTest() {
        val savedCourse = Course(
            null,
            "Spring Security 6 Zero to Master",
            "Security"
        )
        courseRepository.save(savedCourse)

        val updatedCourseDTO = CourseDTO(
            null,
            "Java Microservices: CQRS & Event Sourcing with Kafka",
            "Development"
        )
        val courseDto = webTestClient
            .put()
            .uri("/v1/courses/{id}", savedCourse.id)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(updatedCourseDTO.name, courseDto!!.name, "Name should be same")
        assertEquals(updatedCourseDTO.category, courseDto.category, "Category should be same")
    }
}