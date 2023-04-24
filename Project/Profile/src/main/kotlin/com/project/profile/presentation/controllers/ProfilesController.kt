package com.project.profile.presentation.controllers

import com.project.profile.data.dtos.UserDTO
import com.project.profile.data.entities.UserProfile
import com.project.profile.persistence.repositories.ProfileRepository
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap

@RestController
@CrossOrigin
@RequestMapping("/profile")
class ProfilesController {

    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @GetMapping("/{userId}")
    fun myProfile(@PathVariable userId: Int): ResponseEntity<Any> {
        if (userId < 0) return ResponseEntity("User cannot have id -1!", HttpStatus.FORBIDDEN)

        val user = profileRepository.findUserByIdmId(userId)
        if (user.isEmpty) return ResponseEntity("User not found", HttpStatus.NOT_FOUND)

        return ResponseEntity(user, HttpStatus.FOUND)
    }

    @PostMapping("/new-profile")
    fun newProfile(@RequestBody userDTO: UserDTO): ResponseEntity<String> {

        try {
            // first, check if user already exists, and if so, return error code
            val profile = profileRepository.findUserProfileByEmail(userDTO.getEmail())
            if (profile.isPresent)
                // 409 Conflict
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User with that email already exists")

            val newProfile = UserProfile(userDTO)
            profileRepository.save(newProfile)

            return ResponseEntity.status(HttpStatus.CREATED).body("Profile created successfully")

        } catch (exception: Exception) {
            // 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred while creating a new profile")
        }
    }

    @GetMapping("/check-email/{email}")
    @ResponseBody
    fun emailAvailable(@PathVariable email: String): ResponseEntity<Map<String, Boolean>> {
        val profile = profileRepository.findUserProfileByEmail(email)
        val isAvailable = !profile.isPresent

        val response: MutableMap<String, Boolean> = HashMap()
        response["available"] = isAvailable

        return ResponseEntity.ok(response)
    }



    //todo
    // update, delete
}