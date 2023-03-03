package com.project.idm.presentation.controllers

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import java.security.Principal


@RestController
class IdentityManagementController {

    // todo
    @GetMapping("/")
    fun redirectToGatewayDesiredUrl(principal: Principal): String {
        return "Demo ${principal.name}"
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