package com.project.idm.presentation.controllers

import com.project.idm.business.interfaces.ITokenService
import com.project.idm.business.services.UserValidatorService
import com.project.idm.data.dtos.UserDTO
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
@CrossOrigin
class IdentityManagementController {

    @Autowired
    private lateinit var tokenService: ITokenService

    @Autowired
    private lateinit var userValidatorService: UserValidatorService

    @GetMapping("/")
    fun gatewayRedirect(principal: Principal, response: HttpServletResponse) {

        val token = tokenService.generateToken(principal.name)

        // attach cookieAuthorizationToken to the redirect link
        val cookie = Cookie("cookieAuthorizationToken", token)
        cookie.maxAge = ExpiryTimes.ONE_DAY.seconds.toInt()
        cookie.isHttpOnly = true
        cookie.secure = true

        response.addCookie(cookie)
        response.sendRedirect("http://localhost:8000/home")
    }

    @GetMapping("/register")
    fun register(): Any {

        if (isAuthenticatedAlready()) {
            val redirectView = RedirectView()
            redirectView.url = "http://localhost:8000/home"
            return redirectView

        } else {
            val userModel = UserDTO()
            val modelAndView = ModelAndView("register")
            modelAndView.addObject("user", userModel)
            return modelAndView
        }
    }

    @PostMapping("/register")
    fun register(@ModelAttribute userModel: UserDTO): ModelAndView {
        if (!userValidatorService.isUserRegisterValid(userModel))
            return ModelAndView("register-fail")
        return ModelAndView("register-success")
    }

    // known bug: after logging out, even if logged out already, and going in login, you have to press twice to login
    @GetMapping("/login")
    fun login(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): Any {

        if (isAuthenticatedAlready()) {
            val redirectView = RedirectView()
            redirectView.url = "http://localhost:8001"
            return redirectView

        } else {
            return ModelAndView("login")
        }

    }

    @GetMapping("/logout")
    fun logout(): ModelAndView {
        return ModelAndView("logout")
    }

    @PostMapping("/logout-confirmed")
    fun logoutConfirm(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") encryptedToken: String
    ) {
        // invalidate token if user somehow gets to that uri
        if (encryptedToken != "") {
            tokenService.invalidateEncryptedToken(encryptedToken)
        }

        val auth: Authentication = SecurityContextHolder.getContext().authentication
        SecurityContextLogoutHandler().logout(request, response, auth)
        response.sendRedirect("/login")
    }

    private fun isAuthenticatedAlready(): Boolean {
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        println(auth.authorities.elementAt(0))
        return auth.authorities.isNotEmpty() && auth.authorities.elementAt(0).toString() != "ROLE_ANONYMOUS"
    }
}