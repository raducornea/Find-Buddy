package com.project.gateway.presentation.controllers

import com.project.gateway.business.interfaces.IAuthorizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class DiscoveryController {

    //todo
    // should protect against spam attacks

    @Autowired
    private lateinit var authorizationService: IAuthorizationService

    @GetMapping("/home", "/", "index")
    fun home(
        @RequestHeader(value = "Authorization", required = false, defaultValue = "") bearerJws: String,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String,
    ): ResponseEntity<String> {

        val allowedAuthorities = listOf("read", "write")
        val response = authorizationService.authorize(allowedAuthorities, bearerJws, cookieJws)
        return response
    }
}