package com.project.profile.presentation.controllers

import com.project.profile.persistence.repositories.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/discovery")
class RecommendationController {

    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @GetMapping("/users/{userId}")
    fun all(
        @PathVariable userId: Int,
        @RequestParam(name = "search", defaultValue = "", required = false) search: Optional<String>,
    ): ResponseEntity<Any> {

        if (search.isPresent && search.get() != "") {
            val optionalUsers = profileRepository.findUsersNotMyIDAndMatchingName(userId, search.get())
            return ResponseEntity.ok().body(optionalUsers)
        }

        if (userId < 0) return ResponseEntity("User cannot have id -1!", HttpStatus.FORBIDDEN)

        val allUsersExceptCurrentOne = profileRepository.findAllUsersNotMyId(userId)
        return ResponseEntity.ok().body(allUsersExceptCurrentOne)
    }

//    @GetMapping("/recommended-users")
//    fun recommendedUsersBasedOnMyPreferences() {
//        fun recommendUsers(user: User, users: List<User>): List<User> {
//            return users
//                .filter { it != user } // exclude current user
//                .sortedByDescending { it.preferences.intersect(user.preferences).size } // sort by number of shared preferences
//        }
//    }
}