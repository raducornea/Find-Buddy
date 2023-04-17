package com.project.profile.persistence.repositories

import com.project.profile.data.entities.UserProfile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface ProfileRepository : MongoRepository<UserProfile, String>{

    @Query("{ 'email' : ?0 }")
    fun findUserProfileByEmail(email: String): Optional<UserProfile>

    // todo
    // other function to simply get by relevacny ALL users, without filtering it with firstname/lastname

    //todo
    // should also order by relevancy with preferences
    // - but first have preferences lol
    // those who SHARE the most preferences should be first
    @Query(value = "{ 'idmId' : { \$ne: ?0 }, " +
            "'\$or' : [ " +
            "{ 'firstName' : { \$regex: ?1, \$options: 'i' } }, " +
            "{ 'lastName' : { \$regex: ?1, \$options: 'i' } } " +
            "] }",
    // projection parameter to exclude fields
           fields = "{ 'email' : 0, 'idmId' : 0, 'id' : 0 }")
    fun findUsersNotMyIDAndMatchingName(userID: Int, search: String): Optional<List<UserProfile>>
}