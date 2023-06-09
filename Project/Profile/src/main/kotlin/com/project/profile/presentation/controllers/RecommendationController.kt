package com.project.profile.presentation.controllers

import com.project.profile.business.interfaces.ISortingStrategy
import com.project.profile.business.strategies.sorting.SortByKNNJaccard
import com.project.profile.business.strategies.sorting.SortByKNNCosine
import com.project.profile.business.strategies.sorting.SortByKNNEuclidian
import com.project.profile.business.strategies.sorting.SortByMostPreferences
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

    private lateinit var sortingStrategy: ISortingStrategy

    private fun changeStrategy(strategy: String) {
        sortingStrategy = when (strategy) {
            "knn-cosine" -> SortByKNNCosine()
            "knn-jaccard" -> SortByKNNJaccard()
            "knn-euclidian" -> SortByKNNEuclidian()
            else -> SortByMostPreferences()
        }
    }

    @GetMapping("/users/{userId}")
    fun all(
        @PathVariable userId: Int,
        @RequestParam(name = "search", defaultValue = "", required = false) search: Optional<String>,
        @RequestParam(name = "strategy", defaultValue = "most-preferences", required = false) strategy: String,
        @RequestParam(name = "percentage", defaultValue = "0.7", required = false) percentage: Double,
    ): ResponseEntity<Any> {

        if (search.isPresent && search.get() != "") {
            val optionalUsers = profileRepository.findUsersNotMyIDAndMatchingName(userId, search.get())
            return ResponseEntity.ok().body(optionalUsers)
        }

        if (userId < 0) return ResponseEntity("User cannot have id -1!", HttpStatus.FORBIDDEN)

        val currentUser = profileRepository.findUserByIdmId(userId)
        val allUsers = profileRepository.findAll()

        // make strategy field here in request
        println(strategy)
        changeStrategy(strategy)

        val sortedUsers = sortingStrategy.sort(currentUser.get(), allUsers, percentage)
        return ResponseEntity.ok().body(sortedUsers)
    }
}