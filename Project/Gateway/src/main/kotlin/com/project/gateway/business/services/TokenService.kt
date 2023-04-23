package com.project.gateway.business.services

import com.project.gateway.business.interfaces.IJSONOperationsService
import com.project.gateway.business.interfaces.IUrlCallerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    @Autowired
    private lateinit var jsonOperations: IJSONOperationsService

    @Autowired
    private lateinit var urlCaller: IUrlCallerService

    fun getUserIdFromEncryptedToken(encryptedToken: String): Int {

        val urlValidation = "http://localhost:8001/verify-token"
        try {
            val token = urlCaller.getTokenResponseBody(urlValidation, encryptedToken).toString()

            // decrypt cookie first
            val decoder = Base64.getDecoder()

            val pieces: List<String> = token.split(".")
            val b64payload = pieces[1]

            // user json
            val payloadJSON = String(decoder.decode(b64payload))
            val id = jsonOperations.getIdFromJSONString(payloadJSON)

            return id

        } catch (exception: Exception) {
            return -1
        }
    }

}