package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.dto.CourseDTO
import com.ince.coursecatalogservice.entity.Course
import com.ince.coursecatalogservice.repository.CourseRepository
import com.ince.coursecatalogservice.repository.InstructorRepository
import com.ince.coursecatalogservice.util.courseEntityList
import com.ince.coursecatalogservice.util.instructorEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Testcontainers
class CourseControllerIntTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository
    @Autowired
    lateinit var instructorRepository: InstructorRepository

    companion object {
        @Container
        val postgresDB = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:latest")).apply {
            withDatabaseName("testdb")
            withUsername("postgres")
            withPassword("secret")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresDB::getJdbcUrl)
            registry.add("spring.datasource.username", postgresDB::getUsername)
            registry.add("spring.datasource.password", postgresDB::getPassword)
        }
    }

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        instructorRepository.deleteAll()
        val instructorEntity = instructorEntity()
        instructorRepository.save(instructorEntity)
        courseRepository.saveAll(courseEntityList(instructorEntity))
    }

    @Test
    fun addCourseTest() {
        val instructor = instructorRepository.findAll().first()
        val courseDTO = CourseDTO(null, "Test Name", "Test Category", instructor.id)
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
        Assertions.assertTrue { result!!.id != null }
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
        val instructor = instructorRepository.findAll().first()
        val savedCourse = Course(
            null,
            "Spring Security 6 Zero to Master",
            "Security",
            instructor
        )
        courseRepository.save(savedCourse)

        val updatedCourseDTO = CourseDTO(
            null,
            "Java Microservices: CQRS & Event Sourcing with Kafka",
            "Development",
            instructor.id
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

    @Test
    fun deleteCourseByIdTest() {
        val instructor = instructorRepository.findAll().first()
        val savedCourse = Course(
            null,
            "Spring Security 6 Zero to Master",
            "Security",
            instructor
        )
        courseRepository.save(savedCourse)

        val deleteCourseResult = webTestClient
            .delete()
            .uri("v1/courses/{id}", savedCourse.id)
            .exchange()
            .expectStatus().isNoContent

        assertNotNull(savedCourse.id?.let { courseRepository.findById(it) }, "SavedCourse should be empty")
    }

    @Test
    fun retrieveAllCoursesByName() {
        val uri = UriComponentsBuilder.fromUriString("/v1/courses/course")
            .queryParam("name", "Spring")
            .toUriString()

        val courseDTOList = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(2, courseDTOList!!.size, "Size should be same")
    }
}