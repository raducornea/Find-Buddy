package com.findbuddy.project.business.services

import com.findbuddy.project.business.interfaces.IUserService
import com.findbuddy.project.data.entities.User
import com.findbuddy.project.persistence.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService : IUserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getFilteredUsers(
        pageable: Pageable,
        username: Optional<String>,
        firstName: Optional<String>,
        lastName: Optional<String>,
        match: Optional<String>
    ): Page<User>{
        if (match.isPresent && match.get() == "exact") {
            return userRepository.findAllByFiltersExact(pageable, username.get(), firstName.get(), lastName.get())
        }
        else{
            return userRepository.findAllByFiltersPartial(pageable, username.get(), firstName.get(), lastName.get())
        }
    }
}