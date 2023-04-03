package com.project.idm.persistence.repositories

import com.project.idm.data.entities.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AuthorityRepository : JpaRepository<Authority, String> {

    @Query("select a from Authority a where a.name = :name") // JPQL syntax
    fun findAuthorityByName(name: String): Authority
}