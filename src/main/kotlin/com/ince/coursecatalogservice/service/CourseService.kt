package com.ince.coursecatalogservice.service

import com.ince.coursecatalogservice.dto.CourseDTO
import com.ince.coursecatalogservice.entity.Course
import com.ince.coursecatalogservice.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    companion object: KLogging()

    fun addCourse(courseDTO: CourseDTO): CourseDTO {
        val courseEntity = courseDTO.let {
            Course(
                null,
                it.name,
                it.category
            )
        }
        courseRepository.save(courseEntity)

        logger.info("Saved course is $courseEntity")

        return courseEntity.let {
            CourseDTO(
                it.id,
                it.name,
                it.category
            )
        }
    }
}