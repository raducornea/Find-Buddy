package com.project.idm.business.interfaces

interface ICryptographyService {

    fun encrypt(token: String): String
    fun decrypt(value: String): String
}