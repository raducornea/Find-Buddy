package com.project.profile.business.services

import com.project.profile.business.interfaces.ISortingStrategy
import com.project.profile.data.entities.UserProfile

class SortByKNN : ISortingStrategy {

    override fun sort(currentUser: UserProfile, users: List<UserProfile>): List<UserProfile> {
        val urlStrategy = "http://localhost:5000/algorithms/knn"
        return getMatchingUsersByStrategy(currentUser, users, urlStrategy)
    }
}