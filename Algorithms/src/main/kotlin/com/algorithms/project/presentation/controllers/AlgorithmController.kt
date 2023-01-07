package com.algorithms.project.presentation.controllers

import com.algorithms.project.business.services.SpringSecurityPasswordEncryptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/algorithms")
class AlgorithmController {
    @Autowired
    private lateinit var passwordEncryptionService: SpringSecurityPasswordEncryptionService

    @PostMapping("/encrypt/{password}")
    fun encryptPassword(@PathVariable password: String): ResponseEntity<*> {
        val encryptedPassword = passwordEncryptionService.encryptPassword(password)

        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body("Encrypted password is: $encryptedPassword")
    }
}