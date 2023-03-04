package com.project.gateway.presentation.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
class DiscoveryControlelr {

    @GetMapping("/login")
    fun loginRedirect(): RedirectView {

        val redirectView = RedirectView()
        redirectView.url = "http://localhost:8001/login"
        return redirectView
    }

    @GetMapping("/home", "/", "index")
    fun home(
        @RequestHeader(value = "Authorization", required = false, defaultValue = "") bearerJws: String,
        @CookieValue(value = "cookieAuthorizationToken", defaultValue = "") cookieJws: String
    ): ResponseEntity<String> {

        if (bearerJws == "" && cookieJws == "") {
            return ResponseEntity("400 Missing Authorization Header/Cookie", HttpStatus.BAD_REQUEST)
            // redirect to login page and obtain token
        }

        // 1. verify signature
        return ResponseEntity("cookie $cookieJws, header $bearerJws", HttpStatus.OK)

        // url to call and available roles for that uri
//        val url = "http://localhost:8080/api/songcollection/songs"
//        val allowedRoles = listOf("read")

//        // verify if token is valid and roles are satisfied
//        if (tokenVerifier.verifyToken(bearerJws, allowedRoles) == "TOKEN FAILED")
//            return "Token couldn't be Authorized or is not Valid."
//
//        // get the response string in pretty json if it's parsable or raw string
//        val responseString = urlCaller.getResponse(url)
//
//        // decode the token
//        val decoder = Base64.getDecoder()
//
//        val pieces: List<String> = bearerJws.split(".")
//        val b64payload = pieces[1]
//
//        // user json
//        val payloadJSON = String(decoder.decode(b64payload))
//        val prettyJSON = jsonOperations.prettyJSONString(payloadJSON)
//
//        val userRoles = jsonOperations.geetRolesFromJSONString(payloadJSON)
//        allowedRoles.forEach {
//            if (userRoles.contains(it)){
//                // the actual response if it was allowed
//                println(responseString)
//                return responseString
//            }
//        }
//
//        return "Not allowed"
    }
}