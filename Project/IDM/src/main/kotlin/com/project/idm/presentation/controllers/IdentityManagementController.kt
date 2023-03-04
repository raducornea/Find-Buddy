package com.project.idm.presentation.controllers

import com.project.idm.business.services.TokenService
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


val ONE_HOUR_COOKIE = 1 * 24 * 60
val ONE_DAY_COOKIE = 1 * 24 * 60 * 60
val SEVEN_DAYS_COOKIE = 7 * 24 * 60 * 60

@RestController
class IdentityManagementController {

    @Autowired
    private lateinit var tokenService: TokenService

    @GetMapping("/")
    fun gatewayRedirect(principal: Principal, response: HttpServletResponse) {

        tokenService.generateToken(principal.name)

        val cookie = Cookie("cookieAuthorizationToken", "SUGIPULA123")
        cookie.maxAge = ONE_HOUR_COOKIE

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