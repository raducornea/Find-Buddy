package com.project.gateway.business.interfaces

import org.springframework.http.ResponseEntity

interface IAuthorizationService {

    fun authorize(allowedRoles: List<String>, bearerJws: String, cookieJws: String): ResponseEntity<String>
}