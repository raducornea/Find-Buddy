package com.project.idm.presentation.controllers

import com.google.gson.*
import com.project.idm.business.services.CryptographyService
import com.project.idm.business.services.TokenService
import com.project.idm.persistence.repositories.TokenRepository
import com.project.idm.persistence.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class TokenController {

    @Autowired
    private lateinit var cryptographyService: CryptographyService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var tokenRepository: TokenRepository

    @Autowired
    private lateinit var tokenService: TokenService

    @GetMapping("/verify-token")
    fun verifyToken(
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") encryptedToken: String
    ): String {

        try {
            val token = cryptographyService.decrypt(encryptedToken)

            val message = tokenService.verifyToken(token)
            if (message != "Success!") return message

            return "Bearer: $token"

        } catch (exception: Exception) {
            return "An error occurred while processing the Token, most likely because it was changed. Bye bye!"
        }
    }

    @GetMapping("/invalidate-token")
    fun invalidateToken(
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") encryptedToken: String
    ) {
        tokenService.invalidateEncryptedToken(encryptedToken)
    }


}