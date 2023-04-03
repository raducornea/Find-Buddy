package com.project.gateway.business.interfaces

import org.springframework.http.ResponseEntity

interface IAuthorizationService {

    fun authorize(allowedAuthorities: List<String>, bearerJws: String, cookieJws: String): ResponseEntity<String>
}