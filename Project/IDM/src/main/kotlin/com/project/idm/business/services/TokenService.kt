package com.project.idm.business.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
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
class TokenService {

    @Value("\${very.secret.token.authentication.thingy}")
    private lateinit var tokenSecret: String
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var tokenRepository: TokenRepository
    @Autowired
    private lateinit var cryptographyService: CryptographyService
    private var tokenLifetimeSeconds: Long = ExpiryTimes.ONE_DAY.seconds

    fun generateToken(username: String): String {

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
            .withClaim("roles", authorities.toList())
            .withIssuedAt(nowDate)
            .withNotBefore(nowDate)
            .withExpiresAt(expiryDate)
            .sign(Algorithm.HMAC256(tokenSecret))

        val encryptedToken = cryptographyService.encrypt(token)
        tokenRepository.save(Token(uuid, encryptedToken, userFound.getId(), expiryDate, false))

        return token
    }
}