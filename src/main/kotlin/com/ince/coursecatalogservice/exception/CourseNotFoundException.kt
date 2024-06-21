package com.ince.coursecatalogservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
data class CourseNotFoundException(override val message: String) : RuntimeException(message) {
}
