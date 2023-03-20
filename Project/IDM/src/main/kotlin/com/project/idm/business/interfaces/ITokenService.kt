package com.project.idm.business.interfaces

interface ITokenService {

    fun generateToken(username: String): String
    fun verifySignature(token: String): Boolean
    fun verifyToken(token: String): String
    fun invalidateEncryptedToken(encryptedToken: String)
}