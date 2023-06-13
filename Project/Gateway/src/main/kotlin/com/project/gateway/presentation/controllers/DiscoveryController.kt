package com.project.gateway.presentation.controllers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.project.gateway.business.interfaces.IAuthorizationService
import com.project.gateway.business.interfaces.IUrlCallerService
import com.project.gateway.business.services.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.util.*

@RestController
@RequestMapping("/discovery")
class DiscoveryController {

    //todo
    // should protect against spam attacks

    @Autowired
    private lateinit var urlCaller: IUrlCallerService

    @Autowired
    private lateinit var authorizationService: IAuthorizationService

    @Autowired
    private lateinit var tokenService: TokenService

    @GetMapping("")
    fun discoveryFilters(): ModelAndView{
        return ModelAndView("discovery")
    }

    @GetMapping("/users")
    fun all(
        @RequestHeader(value = "Authorization", required = false, defaultValue = "") bearerJws: String,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String,
//        @RequestParam(name = "search", defaultValue = "", required = false) search: Optional<String>,
        @RequestParam(name = "strategy", defaultValue = "most-preferences", required = false) strategy: String,
        @RequestParam(name = "percentage", defaultValue = "0.7", required = false) percentage: Double,
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
        val url = "http://localhost:8002/discovery/users/${userId}?strategy=${strategy}&percentage=${percentage}"
        val result = urlCaller.getTokenResponseBody(url, cookieJws).toString()
        println(result)

        // status ok
        val objectMapper = ObjectMapper()
        val userList = objectMapper.readValue(result, object : TypeReference<List<Map<String, Any>>>() {})
        val modelAndView = ModelAndView("user-list")
        modelAndView.addObject("users", userList)
        return modelAndView
    }
}