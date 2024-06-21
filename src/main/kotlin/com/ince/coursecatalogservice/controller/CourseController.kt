package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.dto.CourseDTO
import com.ince.coursecatalogservice.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/courses")
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDTO: CourseDTO): ResponseEntity<CourseDTO> {
        return ResponseEntity(courseService.addCourse(courseDTO), HttpStatus.CREATED)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllCourses(): ResponseEntity<List<CourseDTO>> = ResponseEntity(courseService.retrieveAllCourses(), HttpStatus.OK)

    @PutMapping("/{id}")
    fun updateCourse(@RequestBody courseDTO: CourseDTO, @PathVariable id: Int): ResponseEntity<CourseDTO> {
        return ResponseEntity(courseService.updateCourse(id, courseDTO), HttpStatus.OK)
    }
}