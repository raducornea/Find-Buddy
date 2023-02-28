//package com.project.idm.config.security.managers
//
//import com.project.idm.config.security.providers.CustomAuthenticationProvider
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.BadCredentialsException
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.AuthenticationException
//import org.springframework.stereotype.Component
//
//@Component
//class CustomAuthenticationManager(private val provider: CustomAuthenticationProvider) : AuthenticationManager {
//
////    private lateinit var provider: CustomAuthenticationProvider
////    constructor(provider: CustomAuthenticationProvider) : this() {
////        this.provider = provider
////    }
//
//    @Throws(AuthenticationException::class)
//    override fun authenticate(authentication: Authentication): Authentication {
//        if (provider.supports(authentication.javaClass)) {
//            return provider.authenticate(authentication)
//        }
//        throw BadCredentialsException("Oh No!")
//    }
//}