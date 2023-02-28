//package com.project.idm.config.security.providers
//
//import com.project.idm.config.security.authentication.CustomAuthentication
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.security.authentication.AuthenticationProvider
//import org.springframework.security.authentication.BadCredentialsException
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.AuthenticationException
//import org.springframework.stereotype.Component
//
//@Component
//class CustomAuthenticationProvider : AuthenticationProvider {
//
//    @Value("\${our.very.very.very.secret.key}")
//    private val key: String? = null
//
//    @Throws(AuthenticationException::class)
//    override fun authenticate(authentication: Authentication): Authentication {
//        val ca: CustomAuthentication = authentication as CustomAuthentication
//        val headerKey: String? = ca.getKey()
//
//        println("$key + $headerKey")
//
//        if (key == headerKey) {
//            return CustomAuthentication(true, null)
//        }
//        throw BadCredentialsException("Oh No!")
//    }
//
//    override fun supports(authentication: Class<*>): Boolean {
//        return CustomAuthentication::class.java == authentication
//    }
//}
