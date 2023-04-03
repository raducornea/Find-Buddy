package com.project.idm.business.services

import com.project.idm.data.entities.Authority
import com.project.idm.data.entities.User
import com.project.idm.data.models.UserModel
import com.project.idm.persistence.repositories.AuthorityRepository
import com.project.idm.persistence.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
//class UserValidatorService : IUserValidatorService {
class UserValidatorService  {

//    override fun checkUserFieldsValidity(userModel: UserModel): String {
//        return ""
//    }

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var authorityRepository: AuthorityRepository

    fun checkUserFieldsValidity(userModel: UserModel): Boolean {

        val username = userModel.getUsername()
        val password = userModel.getPassword()
        val passwordConfirm = userModel.getPasswordConfirm()

        val validUsername = username.length in 2..20
        val validPassword = password == passwordConfirm && password.length > 2
        val validFields = validUsername && validPassword

        return validFields
    }

    fun isUserRegisterValid(userModel: UserModel): Boolean {

        // 1. validate fields
        val validFields = checkUserFieldsValidity(userModel)
        if (!validFields) return false

        // IMPORTANT: can't insert user before making sure in both DBs we can insert it!
        // that's why we check before posting
        val username = userModel.getUsername()

        // 2. check if it's already in db (IDM Service)
        val userFound = userRepository.findUserByUsername(username)
        if (userFound.isPresent) return false

        // todo
        // 3. check if it's already in db (Profile Service)

        // todo
        // 4. post in IDM Service and Profile Service the User and Profile
        // perform logic to register User here + UserProfle
        try {
            // post in IDM Service
            val passwordEncoder = BCryptPasswordEncoder()
            val hashedPassword = passwordEncoder.encode(userModel.getPassword())

            val authorities = mutableSetOf<Authority>()
            val readAuthority = authorityRepository.findAuthorityByName("read")
            authorities.add(readAuthority)

            val user = User()
            user.setUsername(userModel.getUsername())
            user.setPassword(hashedPassword)
            user.setAuthorities(authorities)

            userRepository.save(user)

            println(userModel.getUsername())
            println(userModel.getPassword())
            println(userModel.getPasswordConfirm())

            //todo
            // post in Profile Service

        } catch (exception: Exception) {
            println("$exception")
            return false
        }

        return true
    }


}