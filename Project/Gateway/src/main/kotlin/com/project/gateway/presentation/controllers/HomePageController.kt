package com.project.gateway.presentation.controllers

import com.project.gateway.business.interfaces.IAuthorizationService
import com.project.gateway.business.interfaces.IUrlCallerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView

@RestController
class HomePageController {

    //todo
    // should protect against spam attacks

    @Autowired
    private lateinit var urlCaller: IUrlCallerService

    @Autowired
    private lateinit var authorizationService: IAuthorizationService

    @GetMapping("/home", "/", "index")
    fun home(
        @RequestHeader(value = "Authorization", required = false, defaultValue = "") bearerJws: String,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String,
    ): Any {

        val allowedAuthorities = listOf("read", "write")
        val response = authorizationService.authorize(allowedAuthorities, bearerJws, cookieJws)

        return if (response.statusCode.is2xxSuccessful) {
            ModelAndView("index")
        } else {
            RedirectView("/logout")
        }
    }
}