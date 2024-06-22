package com.ince.coursecatalogservice.service

import com.ince.coursecatalogservice.dto.InstructorDTO
import com.ince.coursecatalogservice.entity.Instructor
import com.ince.coursecatalogservice.repository.InstructorRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class InstructorService(val instructorRepository: InstructorRepository) {

    @Transactional
    fun createInstructor(instructorDTO: InstructorDTO): InstructorDTO {
        val instructor = Instructor(
            null,
            instructorDTO.name
        )
        instructorRepository.save(instructor)

        return instructor.let {
            InstructorDTO(
                it.id,
                it.name
            )
        }
    }
}