package com.algorithms.project.business.services

import com.algorithms.project.business.interfaces.IPasswordEncryptionService
import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SpringSecurityPasswordEncryptionService : IPasswordEncryptionService {
//    @Autowired
//    lateinit var passwordEncoder: PasswordEncoder

    fun encryptPassword(password: String): String {
//        val encodedPassword = passwordEncoder.encode(password)
//        return encodedPassword
        return "HELLO $password"
    }

}
