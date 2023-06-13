package com.project.gateway.presentation.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.gateway.business.interfaces.IAuthorizationService
import com.project.gateway.business.interfaces.IUrlCallerService
import com.project.gateway.business.services.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

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
    ): ModelAndView {

        val allowedAuthorities = listOf("read")
        val response = authorizationService.authorize(allowedAuthorities, bearerJws, cookieJws)
        if (response.statusCode != HttpStatus.OK) {
            return ModelAndView("logout") // or redirect to the logout page
        }

        val userId = tokenService.getUserIdFromEncryptedToken(cookieJws)
        if (userId == -1) {
            val errorMessage = "User cannot have id -1!"
            val modelAndView = ModelAndView("error")
            modelAndView.addObject("errorMessage", errorMessage)
            return modelAndView
        }

        // proceed to call the other service request and attach the userId to request
        val url = "http://localhost:8002/profile/${userId}"
        val result = urlCaller.getTokenResponseBody(url, cookieJws).toString()
        println(result)

        // status ok
        val objectMapper = ObjectMapper()
        val userJson = objectMapper.readTree(result)
        val userMap = objectMapper.convertValue(userJson, Map::class.java)

        val modelAndView = ModelAndView("profile") // specify the name of the Thymeleaf template
        modelAndView.addObject("user", userMap) // pass the user details as a model attribute
        return modelAndView // return the ModelAndView
    }

}