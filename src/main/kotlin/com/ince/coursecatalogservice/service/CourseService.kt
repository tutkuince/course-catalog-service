package com.ince.coursecatalogservice.service

import com.ince.coursecatalogservice.dto.CourseDTO
import com.ince.coursecatalogservice.entity.Course
import com.ince.coursecatalogservice.exception.CourseNotFoundException
import com.ince.coursecatalogservice.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    companion object : KLogging()

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

    fun retrieveAllCourses(): List<CourseDTO> {
        return courseRepository.findAll()
            .map {
                CourseDTO(
                    it.id,
                    it.name,
                    it.category
                )
            }
    }

    fun updateCourse(id: Int, courseDTO: CourseDTO): CourseDTO {
        val existingCourse = courseRepository.findById(id)
        return if (existingCourse.isPresent) {
            existingCourse.get()
                .let {
                    it.name = courseDTO.name ?: it.name
                    it.category = courseDTO.category ?: it.category
                    courseRepository.save(it)
                    CourseDTO(
                        it.id,
                        it.name,
                        it.category
                    )
                }
        } else {
            throw CourseNotFoundException("No course found for the passed in Id: $id")
        }
    }

    fun deleteCourseById(id: Int) {
        val existingCourse = courseRepository.findById(id)
        if (existingCourse.isPresent) {
            existingCourse.let {
                courseRepository.deleteById(id)
            }
        } else {
            throw CourseNotFoundException("No course found for the passed in Id: $id")
        }
    }
}