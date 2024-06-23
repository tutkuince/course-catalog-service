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
        val instructorEntity = instructorDTO.let {
            Instructor(
                it.id,
                it.name
            )
        }
        instructorRepository.save(instructorEntity)

        return instructorEntity.let {
            InstructorDTO(
                it.id,
                it.name
            )
        }
    }
}