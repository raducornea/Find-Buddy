package com.project.idm.presentation.controllers

import com.project.idm.business.interfaces.ITokenService
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
    private lateinit var tokenService: ITokenService

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
            val userModel = UserModel()
            val modelAndView = ModelAndView("register")
            modelAndView.addObject("user", userModel)
            return modelAndView
        }
    }

    @PostMapping("/register")
    fun register(@ModelAttribute userModel: UserModel): ModelAndView {

        // perform logic to register User here + UserProfle
        println(userModel.getUsername())
        println(userModel.getPassword())
        println(userModel.getPasswordConfirm())

        //        val passwordEncoder = BCryptPasswordEncoder()
        //        val password = "12345"
        //        val hashedPassword = passwordEncoder.encode(password)
        //        println(hashedPassword)

        // should return DIFFERENT ModelAndviews depending on the status of posting the resource
        return ModelAndView("register-success")
    }

    // known bug: after logging out, even if logged out already, and going in login, you have to press twice to log in
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