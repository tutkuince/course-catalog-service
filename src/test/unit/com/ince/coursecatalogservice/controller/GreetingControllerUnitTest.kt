package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {

    /**
     * Unit test is a kind of test which tests only the class and method of interest and mocks the next layer of the code
     *
     * */
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingServiceMock: GreetingService

    /**
     * Integration test is a kind of test which actually test the application end to end
     *
     * */
    @Test
    fun retrieveGreetingTest() {
        val name = "Tutku"

        every { greetingServiceMock.retrieveGreeting(any()) } returns "$name, Hello from default profile"

        val result = webTestClient.get()
            .uri("/v1/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        assertEquals("$name, Hello from default profile", result.responseBody)
    }

}