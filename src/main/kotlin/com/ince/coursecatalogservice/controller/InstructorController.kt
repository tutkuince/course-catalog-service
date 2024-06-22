package com.ince.coursecatalogservice.controller

import com.ince.coursecatalogservice.dto.InstructorDTO
import com.ince.coursecatalogservice.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/instructors")
@Validated
class InstructorController(val instructorService: InstructorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@RequestBody instructorDTO: InstructorDTO): ResponseEntity<InstructorDTO> {
        return ResponseEntity(instructorService.createInstructor(instructorDTO), HttpStatus.CREATED)
    }
}