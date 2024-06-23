package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.dto.InstructorDTO
import com.ince.coursecatalogservice.service.InstructorService
import com.ince.coursecatalogservice.util.instructorDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorServiceMockk: InstructorService

    @Test
    fun createInstructorTest() {
        val instructorDTO: InstructorDTO = InstructorDTO(null, "Robert Cecil Martin")

        every { instructorServiceMockk.createInstructor(any()) } returns instructorDTO(1)

        val result = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Robert Cecil Martin", result?.name, "Instructor Name must be same")
        Assertions.assertTrue {
            result!!.id != null
        }
    }
}