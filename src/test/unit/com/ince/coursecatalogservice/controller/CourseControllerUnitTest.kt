package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.dto.CourseDTO
import com.ince.coursecatalogservice.entity.Course
import com.ince.coursecatalogservice.service.CourseService
import com.ince.coursecatalogservice.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMockk: CourseService

    @Test
    fun addCourseTest() {
        val courseDTO = CourseDTO(null, "Test Name", "Test Category", 1)

        every { courseServiceMockk.addCourse(any()) } returns courseDTO(id = 1)

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
        Assertions.assertTrue { result!!.id != null }
    }

    @Test
    fun retrieveAllCoursesTest() {

        every { courseServiceMockk.retrieveAllCourses() }.returnsMany(
            listOf(
                courseDTO(1),
                courseDTO(2, name = "Spring Security 6 Zero to Master")
            )
        )

        val courseDTOList = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(2, courseDTOList!!.size, "Size should be same")
    }

    @Test
    fun updateCourseTest() {
        val savedCourse = Course(
            null,
            "Spring Security 6 Zero to Master",
            "Security"
        )

        every { courseServiceMockk.updateCourse(any(), any()) } returns courseDTO(100, "Java Microservices: CQRS & Event Sourcing with Kafka")

        val updatedCourseDTO = CourseDTO(
            null,
            "Java Microservices: CQRS & Event Sourcing with Kafka",
            "Development"
        )
        val courseDto = webTestClient
            .put()
            .uri("/v1/courses/{id}", 100)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(updatedCourseDTO.name, courseDto!!.name, "Name should be same")
        assertEquals(updatedCourseDTO.category, courseDto.category, "Category should be same")
    }

    /*@Test
    fun deleteCourseByIdTest() {
        every { courseServiceMockk.deleteCourseById(any()) } just runs  // simulate mock call no return value

        val deleteCourseResult = webTestClient
            .delete()
            .uri("v1/courses/{id}", 1)
            .exchange()
            .expectStatus().isNoContent
    }*/
}