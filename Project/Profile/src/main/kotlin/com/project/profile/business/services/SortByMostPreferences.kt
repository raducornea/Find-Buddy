package com.project.profile.business.services

import com.project.profile.business.interfaces.ISortingStrategy
import com.project.profile.data.entities.UserProfile
import org.springframework.stereotype.Service

@Service
class SortByMostPreferences : ISortingStrategy {

    override fun sort(currentUser: UserProfile, users: List<UserProfile>): List<UserProfile> {
        return users
            // exclude current user
            .filter { it.getId() != currentUser.getId() }

            // sort by number of shared preferences
            .sortedByDescending { it.getPreferences().intersect(currentUser.getPreferences().toSet()).size }
    }

}