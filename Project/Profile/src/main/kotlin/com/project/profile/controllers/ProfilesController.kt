package com.project.profile.controllers

import com.project.profile.data.dtos.UserDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfilesController {

    @PostMapping("/hello")
    fun hello(@RequestBody userDTO: UserDTO): String {
        println(userDTO.getUsername())
        return "This is ok: ${userDTO.getUsername()} "
    }
}