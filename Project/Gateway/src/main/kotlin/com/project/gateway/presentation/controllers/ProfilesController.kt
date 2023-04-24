package com.project.gateway.presentation.controllers

import com.project.gateway.business.interfaces.IAuthorizationService
import com.project.gateway.business.interfaces.IUrlCallerService
import com.project.gateway.business.services.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profile")
class ProfilesController {

    @Autowired
    private lateinit var urlCaller: IUrlCallerService

    @Autowired
    private lateinit var authorizationService: IAuthorizationService

    @Autowired
    private lateinit var tokenService: TokenService

    @GetMapping("")
    fun myProfile(
        @RequestHeader(value = "Authorization", required = false, defaultValue = "") bearerJws: String,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String,
    ): ResponseEntity<String> {

        val allowedAuthorities = listOf("read")
        val response = authorizationService.authorize(allowedAuthorities, bearerJws, cookieJws)
        if (response.statusCode != HttpStatus.OK) {
            return response
        }

        val userId = tokenService.getUserIdFromEncryptedToken(cookieJws)
        if (userId == -1) return ResponseEntity("User cannot have id -1!", HttpStatus.FORBIDDEN)

        // proceed to call the other service request and attach the userId to request
        val url = "http://localhost:8002/profile/${userId}"
        val result = urlCaller.getTokenResponseBody(url, cookieJws).toString()

        println(result)
        return ResponseEntity.ok(result)
    }

}