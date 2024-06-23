package com.ince.coursecatalogservice.service

import com.ince.coursecatalogservice.dto.InstructorDTO
import com.ince.coursecatalogservice.entity.Instructor
import com.ince.coursecatalogservice.exception.CourseNotFoundException
import com.ince.coursecatalogservice.exception.InstructorNotFoundException
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

    fun findInstructorById(instructorId: Int): InstructorDTO {
        val instructorOptional = instructorRepository.findById(instructorId)
        if (instructorOptional.isPresent) {
            return instructorOptional.get().let {
                InstructorDTO(
                    it.id,
                    it.name
                )
            }
        } else {
            throw InstructorNotFoundException("No instructor found for the passed in Id: $instructorId")
        }
    }
}