package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.dto.CourseDTO
import com.ince.coursecatalogservice.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDTO: CourseDTO): ResponseEntity<CourseDTO> {
        return ResponseEntity(courseService.addCourse(courseDTO), HttpStatus.CREATED)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllCourses(): ResponseEntity<List<CourseDTO>> = ResponseEntity(courseService.retrieveAllCourses(), HttpStatus.OK)

    @PutMapping("/{id}")
    fun updateCourse(@RequestBody courseDTO: CourseDTO, @PathVariable id: Int): ResponseEntity<CourseDTO> {
        return ResponseEntity(courseService.updateCourse(id, courseDTO), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourseById(@PathVariable id: Int) = ResponseEntity(courseService.deleteCourseById(id), HttpStatus.NO_CONTENT)
}