package com.project.idm.presentation.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.idm.business.interfaces.ITokenService
import com.project.idm.business.services.UserValidatorService
import com.project.idm.data.dtos.UserDTO
import com.project.idm.data.tokens.ExpiryTimes
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
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

    // todo make preferences in lower case and make sure no commas are included and the limits are respected
    @PostMapping("/register")
    fun register(@ModelAttribute userModel: UserDTO): ModelAndView {
        if (!userValidatorService.isUserRegisterValid(userModel))
            return ModelAndView("register-fail")

        // upon success, make sure to register the preferences in knn algoritmh too for python
        val urlRegisterCallback = "http://localhost:5000/algorithms/register"
        val jsonMap = mutableMapOf<String, Any?>()
        jsonMap.put("new_preferences", userModel.getPreferences())
        val response = postRequest(jsonMap, urlRegisterCallback)
        println(response)

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

    private fun postRequest(values: Any?, url: String): String {
        val objectMapper = ObjectMapper()
        val requestBody: String = objectMapper
            .writeValueAsString(values)

        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return response.body()
    }
}