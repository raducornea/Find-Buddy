package com.project.profile.business.services

import com.project.profile.business.interfaces.ISortingStrategy
import com.project.profile.data.entities.UserProfile

class SortByCosineSimilarity : ISortingStrategy {

    override fun sort(currentUser: UserProfile, users: List<UserProfile>): List<UserProfile> {
        val urlStrategy = "http://localhost:5000/algorithms/cosine-similarity"
        return getMatchingUsersByStrategy(currentUser, users, urlStrategy)
    }
}