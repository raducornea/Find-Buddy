package com.project.idm.persistence.repositories

import com.project.idm.data.entities.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TokenRepository : JpaRepository<Token, String> {

    @Query("select t from Token t where t._uuid = :uuid") // JPQL syntax
    fun findTokenByUuid(uuid: String): Token

    @Query("select t from Token t where t.token = :token") // JPQL syntax
    fun findTokenByToken(token: String): Token
}