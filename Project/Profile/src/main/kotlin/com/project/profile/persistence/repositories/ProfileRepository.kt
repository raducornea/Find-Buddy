package com.project.profile.persistence.repositories

import com.project.profile.data.entities.UserProfile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface ProfileRepository : MongoRepository<UserProfile, String>{

    @Query("{ 'email' : ?0 }")
    fun findUserProfileByEmail(email: String): Optional<UserProfile>
}