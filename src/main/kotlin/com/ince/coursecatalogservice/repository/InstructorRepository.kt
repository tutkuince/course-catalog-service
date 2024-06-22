package com.ince.coursecatalogservice.repository

import com.ince.coursecatalogservice.entity.Instructor
import org.springframework.data.jpa.repository.JpaRepository

interface InstructorRepository: JpaRepository<Instructor, Int> {
}