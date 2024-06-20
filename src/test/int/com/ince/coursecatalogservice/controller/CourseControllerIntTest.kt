package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.dto.CourseDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.bind.annotation.ResponseBody
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun addCourseTest() {
        val courseDTO = CourseDTO(null, "Test Name", "Test Category")
        val result = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Test Name", courseDTO.name, "Name should be same")
        assertEquals("Test Category", courseDTO.category, "Category Name should be same")
        // assertNotNull(result?.id, "Result cannot be null")
        Assertions.assertTrue{ result!!.id != null }
    }
}