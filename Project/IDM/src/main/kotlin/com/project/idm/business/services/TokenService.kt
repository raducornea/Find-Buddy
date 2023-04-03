package com.project.idm.business.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.project.idm.business.interfaces.ICryptographyService
import com.project.idm.business.interfaces.ITokenService
import com.project.idm.config.security.SecurityUser
import com.project.idm.data.entities.Token
import com.project.idm.data.entities.User
import com.project.idm.data.tokens.ExpiryTimes
import com.project.idm.persistence.repositories.TokenRepository
import com.project.idm.persistence.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*


@Service
class TokenService : ITokenService {

    @Value("\${very.secret.token.authentication.thingy}")
    private lateinit var tokenSecret: String
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var tokenRepository: TokenRepository
    @Autowired
    private lateinit var cryptographyService: ICryptographyService
    private var tokenLifetimeSeconds: Long = ExpiryTimes.ONE_DAY.seconds

    override fun generateToken(username: String): String {

        val userFound: User = userRepository.findUserByUsername(username).get()
        val securityUser = SecurityUser(userFound)

        val authorities = securityUser
            .authorities.map { it.authority }
            .toList()

        val currentTimeEpocSec = Instant.now().epochSecond
        val nowDate = Date.from(Instant.ofEpochSecond(currentTimeEpocSec))
        val expiryDate = Date.from(Instant.ofEpochSecond(currentTimeEpocSec + tokenLifetimeSeconds))
        val uuid = UUID.randomUUID().toString()

        val token = JWT.create()
            .withJWTId(uuid)
            .withClaim("id", userFound.getId())
            .withClaim("username", username)
            .withClaim("password", securityUser.password)
            .withClaim("authorities", authorities.toList())
            .withIssuedAt(nowDate)
            .withNotBefore(nowDate)
            .withExpiresAt(expiryDate)
            .sign(Algorithm.HMAC256(tokenSecret))

        val encryptedToken = cryptographyService.encrypt(token)
        tokenRepository.save(Token(uuid, encryptedToken, userFound.getId(), expiryDate, false))
        return encryptedToken
    }

    override fun verifySignature(token: String): Boolean {

        try {
            val algorithm = Algorithm.HMAC256(tokenSecret)
            val verifier: JWTVerifier = JWT.require(algorithm).build()
            verifier.verify(token)
            return true

        } catch (e: Exception) {
            return false
        }
    }

    override fun verifyToken(token: String): String {

        // decode the token
        val decoder = Base64.getDecoder()

        val pieces: List<String> = token.split(".")
        if (pieces.size != 3) return "Not correctly formated Token. Bye bye!"
        val b64payload = pieces[1]

        // verify signature
        val signedToken = verifySignature(token)
        if (!signedToken) return "Not signed Token. Bye bye!"

        // user json
        val payloadJSON = String(decoder.decode(b64payload))

        // code might crash, so it's better to return something rather than letting it crash
        try {
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val jsonObject: JsonObject = gson.fromJson(payloadJSON, JsonObject::class.java)

            // verify token expiration date
            val jsonElementExpiration: JsonElement = jsonObject.getAsJsonPrimitive("exp")
            val tokenExpirySeconds: Long = gson.fromJson(jsonElementExpiration, Long::class.java)
            val currentTimeEpocSec = Instant.now().epochSecond
            if (currentTimeEpocSec > tokenExpirySeconds) return "Token Expired. Bye bye!"

            // verify uuid
            val jsonElementUuid: JsonElement = jsonObject.getAsJsonPrimitive("jti")
            val tokenUuid: String = gson.fromJson(jsonElementUuid, String::class.java)

            // token should be in database - verify it
            val databaseToken = tokenRepository.findTokenByUuid(tokenUuid)
            if (databaseToken.getRevocationFlag()) return "Blacklisted Token. Bye bye!"
            if (cryptographyService.decrypt(databaseToken.getToken()) != token) return "Modified Token. Bye bye!"

        } catch (exception: Exception) {
            return "An error occurred while processing the Token. Please Login to get a new one. Bye bye!"
        }

        return "Success!"
    }

    override fun invalidateEncryptedToken(encryptedToken: String) {

        try {

            val databaseToken = tokenRepository.findTokenByToken(encryptedToken)
            databaseToken.setRevocationFlag(true)
            tokenRepository.save(databaseToken)

        } catch (exception: Exception) {
            println("Skiadoodle, Skiadoodle... Your code is now a noodle!")
        }
    }
}