package com.project.profile.persistence.repositories

import com.project.profile.data.entities.UserProfile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface ProfileRepository : MongoRepository<UserProfile, String>{

    @Query("{ 'idmId' : ?0 }")
    fun findUserByIdmId(idmId: Int): Optional<UserProfile>

    @Query("{ 'email' : ?0 }")
    fun findUserProfileByEmail(email: String): Optional<UserProfile>

    @Query(
        value = "{ 'idmId' : { \$ne: ?0 } }",
        fields = "{ 'email' : 0, 'idmId' : 0, 'id' : 0 }")
    fun findAllUsersNotMyId(userId: Int): Optional<List<UserProfile>>
	
    @Query(
        value = "{ 'idmId' : { \$ne: ?0 }, " +
                "'\$or' : [ " +
                "{ 'firstName' : { \$regex: ?1, \$options: 'i' } }, " +
                "{ 'lastName' : { \$regex: ?1, \$options: 'i' } } " +
                "] }",
        // projection parameter to exclude fields
       fields = "{ 'email' : 0, 'idmId' : 0, 'id' : 0 }")
    fun findUsersNotMyIDAndMatchingName(userID: Int, search: String): Optional<List<UserProfile>>


}
