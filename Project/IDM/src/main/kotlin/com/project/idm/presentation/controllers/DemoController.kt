package com.project.idm.presentation.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
//@RequestMapping("/api")
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