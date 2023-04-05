package com.project.idm.presentation.controllers

import com.project.idm.persistence.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserValidationController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("/check-username/{username}")
    @ResponseBody
    fun usernameAvailable(@PathVariable username: String): ResponseEntity<Map<String, Boolean>> {
        val user = userRepository.findUserByUsername(username)
        val isAvailable = !user.isPresent

        val response: MutableMap<String, Boolean> = HashMap()
        response["available"] = isAvailable

        return ResponseEntity.ok(response)
    }
}