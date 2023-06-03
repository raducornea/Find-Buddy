package com.project.profile.business.strategies.sorting

import com.project.profile.business.interfaces.ISortingStrategy
import com.project.profile.data.entities.UserProfile

class SortByMostPreferences : ISortingStrategy {

    override fun sort(currentUser: UserProfile, allUsers: List<UserProfile>, percentage: Double): List<UserProfile> {
        return allUsers
            .filter { it.getId() != currentUser.getId() } // exclude current user
            .sortedByDescending { it.getPreferences().intersect(currentUser.getPreferences().toSet()).size } // sort by number of shared preferences
    }
}