package com.project.profile.presentation.controllers

import com.project.profile.business.interfaces.ISortingStrategy
import com.project.profile.business.services.SortByMostPreferences
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

    @Autowired
    private lateinit var sortingStrategy: ISortingStrategy

    private fun changeStrategy(strategy: String) {
        if (strategy == "MostPreferences") sortingStrategy = SortByMostPreferences()
//        if (strategy == "LeastPreferences") sortingStrategy = SortByMostPreferences()
    }

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

        val currentUser = profileRepository.findUserByIdmId(userId)
        val allUsersExceptCurrentOne = profileRepository.findAllUsersNotMyId(userId)

        // make strategy field here in request
        val strategy = "MostPreferences"
        changeStrategy(strategy)

        val sortedUsers = sortingStrategy.sort(currentUser.get(), allUsersExceptCurrentOne.get())
        return ResponseEntity.ok().body(sortedUsers)
    }
}