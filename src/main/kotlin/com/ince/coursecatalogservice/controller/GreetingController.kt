package com.ince.coursecatalogservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController {

    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable name: String): String {
        return "Hello $name"
    }
}