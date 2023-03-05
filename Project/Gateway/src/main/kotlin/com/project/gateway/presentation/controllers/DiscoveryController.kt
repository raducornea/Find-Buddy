package com.project.gateway.presentation.controllers

import com.project.gateway.business.services.JSONOperations
import com.project.gateway.business.services.UrlCaller
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import java.net.URI
import java.util.*

@RestController
class DiscoveryController {

    @Autowired
    private lateinit var urlCaller: UrlCaller

    @Autowired
    private lateinit var jsonOperations: JSONOperations

    @GetMapping("/login")
    fun loginRedirect(): RedirectView {

        val redirectView = RedirectView()
        redirectView.url = "http://localhost:8001/login"
        return redirectView
    }

    @GetMapping("/logout")
    fun logoutRedirect(
        response: HttpServletResponse,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String,
    ) {

        // invalidate cookie
        // todo fix this
        val url = "http://localhost:8001/invalidate-token"
        urlCaller.getTokenResponseBody(url, cookieJws).toString()

        // remove cookie from client too
        val cookie = Cookie("cookieAuthorizationToken", null)
        cookie.maxAge = 0
        cookie.secure = true
        cookie.isHttpOnly = true
        cookie.path = "/"

        response.addCookie(cookie);
        response.sendRedirect("http://localhost:8001/login")

//        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8001/login")).build();
    }

    @GetMapping("/home", "/", "index")
    fun home(
        @RequestHeader(value = "Authorization", required = false, defaultValue = "") bearerJws: String,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String,
    ): ResponseEntity<String> {

        val allowedRoles = listOf("read", "write")

        var token = if (cookieJws == "") bearerJws else cookieJws
        if (token == "") {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8000/login")).build();
        }

        val urlValidation = "http://localhost:8001/verify-token"
        try {

            token = urlCaller.getTokenResponseBody(urlValidation, token).toString()

            val parts: List<String> = token.split(" ")
            if (parts.elementAt(0) != "Bearer:") return ResponseEntity("UNNACEPTABLE!!!", HttpStatus.NOT_ACCEPTABLE)
            token = parts[1]

            // decode the token
            val decoder = Base64.getDecoder()

            val pieces: List<String> = token.split(".")
            val b64payload = pieces[1]

            // user json
            val payloadJSON = String(decoder.decode(b64payload))

            val userRoles = jsonOperations.getAuthoritiesFromJSONString(payloadJSON)
            allowedRoles.forEach {
                if (userRoles.contains(it)){
                    // the actual response if it was allowed
                    return ResponseEntity("Allowed!!!! YAAAAY", HttpStatus.OK)
                }
            }

            return ResponseEntity("Not allowed!!!! Nooo :(", HttpStatus.METHOD_NOT_ALLOWED)


        } catch (exception: Exception) {
            return ResponseEntity("An error occurred at server.", HttpStatus.BAD_REQUEST)

        }
    }
}