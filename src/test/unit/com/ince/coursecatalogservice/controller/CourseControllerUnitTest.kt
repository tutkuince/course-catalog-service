package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.dto.CourseDTO
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
        val courseDTO = CourseDTO(null, "Test Name", "Test Category")

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
}