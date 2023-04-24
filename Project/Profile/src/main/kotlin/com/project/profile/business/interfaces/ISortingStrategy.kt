package com.project.profile.business.interfaces

import com.project.profile.data.entities.UserProfile

interface ISortingStrategy {

    fun sort(currentUser: UserProfile, users: List<UserProfile>): List<UserProfile>
}
