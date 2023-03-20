package com.project.gateway.business.services

import com.project.gateway.business.interfaces.IAuthorizationService
import com.project.gateway.business.interfaces.IJSONOperationsService
import com.project.gateway.business.interfaces.IUrlCallerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.net.URI
import java.util.*

@Service
class AuthorizationService : IAuthorizationService {

    @Autowired
    private lateinit var urlCaller: IUrlCallerService

    @Autowired
    private lateinit var jsonOperations: IJSONOperationsService

    override fun authorize(allowedRoles: List<String>, bearerJws: String, cookieJws: String): ResponseEntity<String> {
        var token = if (cookieJws == "") bearerJws else cookieJws
        if (token == "") {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8000/login")).build()
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