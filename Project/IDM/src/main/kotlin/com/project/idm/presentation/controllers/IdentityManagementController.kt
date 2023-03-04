package com.project.idm.presentation.controllers

import com.project.idm.business.services.TokenService
import com.project.idm.data.tokens.ExpiryTimes
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import java.security.Principal

@RestController
class IdentityManagementController {

    @Autowired
    private lateinit var tokenService: TokenService

    @GetMapping("/")
    fun gatewayRedirect(principal: Principal, response: HttpServletResponse) {

        val token = tokenService.generateToken(principal.name)

        val cookie = Cookie("cookieAuthorizationToken", token)
        cookie.maxAge = ExpiryTimes.ONE_DAY.seconds.toInt()

        response.addCookie(cookie);
        response.sendRedirect("http://localhost:8000/home")
    }

    @GetMapping("/hello")
    fun hello(): ModelAndView {
        return ModelAndView("hello")
    }

    @GetMapping("/login")
    fun login(): ModelAndView {
        return ModelAndView("login")
    }

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse) {

        val auth: Authentication = SecurityContextHolder.getContext().authentication
        SecurityContextLogoutHandler().logout(request, response, auth)

        response.sendRedirect("/login");
    }
}