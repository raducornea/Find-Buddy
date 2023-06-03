package com.project.profile.business.strategies.sorting

import com.project.profile.business.interfaces.ISortingStrategy
import com.project.profile.data.entities.UserProfile

class SortByKNNEuclidian : ISortingStrategy {

    override fun sort(currentUser: UserProfile, allUsers: List<UserProfile>, percentage: Double): List<UserProfile> {
        val urlStrategy = "http://localhost:5000/algorithms/knn/euclidian"
        return getMatchingUsersByStrategy(currentUser, allUsers, urlStrategy, percentage)
    }
}