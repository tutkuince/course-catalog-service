package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.dto.InstructorDTO
import com.ince.coursecatalogservice.repository.InstructorRepository
import org.junit.jupiter.api.Assertions
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
class InstructorControllerIntTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun createInstructorTest() {
        val instructorDTO: InstructorDTO = InstructorDTO(null, "Tutku Ince")
        val result = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Tutku Ince", result?.name, "Instructor Name must be same")
        Assertions.assertTrue {
            result!!.id != null
        }
    }
}