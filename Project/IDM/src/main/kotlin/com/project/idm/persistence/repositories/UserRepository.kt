package com.project.idm.persistence.repositories

import com.project.idm.data.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User, Int> {

    @Query("select u from User u where u.username = :username") // JPQL syntax
    fun findUserByUsername(username: String): Optional<User>
}