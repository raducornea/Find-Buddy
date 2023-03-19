package com.project.idm.presentation.controllers

import com.project.idm.business.services.TokenService
import com.project.idm.data.models.UserModel
import com.project.idm.data.tokens.ExpiryTimes
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
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
        cookie.isHttpOnly = true
        cookie.secure = true

        response.addCookie(cookie);
        response.sendRedirect("http://localhost:8000/home")
    }

    @GetMapping("/register")
    fun register(): Any {

        val auth: Authentication = SecurityContextHolder.getContext().authentication
        println(auth.authorities.elementAt(0))

        // not authenticated
        if (auth.authorities.isNotEmpty() && auth.authorities.elementAt(0).toString() != "ROLE_ANONYMOUS") {
            val redirectView = RedirectView()
            redirectView.url = "http://localhost:8000/home"

            return redirectView

        // authenticated
        } else {
            val userModel = UserModel()
            val modelAndView = ModelAndView("register")
            modelAndView.addObject("user", userModel)

            return modelAndView
        }
    }

    @PostMapping("/register")
    fun register(@ModelAttribute userModel: UserModel): ModelAndView {
        // should protect against spam attacks

        println(userModel.getUsername())
        println(userModel.getPassword())
        println(userModel.getPasswordConfirm())

        return ModelAndView("hello")
    }

    // known bug: after logging out, even if logged out already, and going in login, you have to press twice to log in
    @GetMapping("/login")
    fun login(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ModelAndView {

        return ModelAndView("login")
    }

    @GetMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {

        val auth: Authentication = SecurityContextHolder.getContext().authentication
        SecurityContextLogoutHandler().logout(request, response, auth)
        SecurityContextHolder.clearContext()

        // remove cookie from client too
        val cookieAuthorizationToken = Cookie("cookieAuthorizationToken", null)
        cookieAuthorizationToken.maxAge = 0
        cookieAuthorizationToken.secure = true
        cookieAuthorizationToken.isHttpOnly = true
        cookieAuthorizationToken.path = "/"

        response.addCookie(cookieAuthorizationToken)
        response.sendRedirect("/login")
    }
}