package com.project.gateway.presentation.controllers


import com.project.gateway.business.interfaces.IUrlCallerService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
class IDMController {

    @Autowired
    private lateinit var urlCaller: IUrlCallerService

    @GetMapping("/login")
    fun loginRedirect(
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String,
    ): RedirectView {

        if (cookieJws == ""){
            val redirectView = RedirectView()
            redirectView.url = "http://localhost:8001/login"
            return redirectView

        } else {
            val redirectView = RedirectView()
            redirectView.url = "http://localhost:8000/"
            return redirectView
        }

    }

    @GetMapping("/logout")
    fun logoutRedirect(
        response: HttpServletResponse,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String,
    ) {

        // invalidate cookie
        val url = "http://localhost:8001/invalidate-token"
        urlCaller.getTokenResponseBody(url, cookieJws).toString()

        // remove cookie from client too
        val cookieAuthorizationToken = Cookie("cookieAuthorizationToken", null)
        cookieAuthorizationToken.maxAge = 0
        cookieAuthorizationToken.secure = true
        cookieAuthorizationToken.isHttpOnly = true
        cookieAuthorizationToken.path = "/"

        response.addCookie(cookieAuthorizationToken)
        response.sendRedirect("http://localhost:8001/logout")
    }

    @GetMapping("/register")
    fun registerRedirect(
        response: HttpServletResponse,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String,
    ) {

        // remove cookie from client too
        val cookieAuthorization = Cookie("cookieAuthorizationToken", cookieJws)
        response.addCookie(cookieAuthorization)
        response.sendRedirect("http://localhost:8001/register")
    }

    @GetMapping("/funny")
    fun funny(
        response: HttpServletResponse,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String,
    ) {

        val url = "http://localhost:8002/profile/hi"
        val result = urlCaller.getTokenResponseBody(url, cookieJws).toString()
        println(result)
    }
}