package com.findbuddy.project.persistence.repositories

import com.findbuddy.project.data.entities.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Int> {

    @Query(
        value = "select * from users " +
                "where (username=:userName or :userName='') " +
                "and (first_name=:firstName or :firstName='') " +
                "and (last_name=:lastName or :lastName='')",
        nativeQuery = true
    )
    fun findAllByFiltersExact(
        pageable: Pageable,
        @Param("userName")
        username: String,
        @Param("firstName")
        firstName: String,
        @Param("lastName")
        lastName: String
    ): Page<User>

    @Query(
        value = "select * from users " +
                "where (username like %:userName%) " +
                "and (first_name like %:firstName%) " +
                "and (last_name like %:lastName%)",
        nativeQuery = true
    )
    fun findAllByFiltersPartial(
        pageable: Pageable,
        @Param("userName")
        username: String,
        @Param("firstName")
        firstName: String,
        @Param("lastName")
        lastName: String
    ): Page<User>
}