package com.findbuddy.project.business.interfaces

import com.findbuddy.project.data.entities.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface IUserService {
    fun getAllUsers(): List<User>
    fun getFilteredUsers(
        pageable: Pageable,
        username: Optional<String>,
        firstName: Optional<String>,
        lastName: Optional<String>,
        match: Optional<String>
    ): Page<User>
}