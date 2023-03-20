package com.project.idm.presentation.controllers

import com.project.idm.business.interfaces.ICryptographyService
import com.project.idm.business.interfaces.ITokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TokenController {

    @Autowired
    private lateinit var cryptographyService: ICryptographyService

    @Autowired
    private lateinit var tokenService: ITokenService

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