package com.project.idm.business.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.project.idm.config.security.SecurityUser
import com.project.idm.data.entities.User
import com.project.idm.persistence.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class TokenService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Value("\${very.secret.token.authentication.thingy}")
    private lateinit var secret: String

    fun generateToken(username: String) {

        val userFound: User = userRepository.findUserByUsername(username).get()
        val securityUser = SecurityUser(userFound)

        val authorities = securityUser
            .authorities.map { it.authority }
            .toList()

        val token = JWT.create()
            .withIssuer("auth0")
            .withClaim("username", username)
            .withClaim("password", securityUser.password)
            .withClaim("roles", authorities.toList())
            .sign(Algorithm.HMAC256(secret))

        println(token)
    }
}