package com.project.idm.presentation.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController {

    @GetMapping("/hello")
    fun hello(): String {
        return "Hello!"
    }

    @GetMapping("/demo")
    fun demo(): String {
        return "Demo"
    }
}