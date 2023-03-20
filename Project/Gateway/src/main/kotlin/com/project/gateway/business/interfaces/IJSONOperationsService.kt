package com.project.gateway.business.interfaces

interface IJSONOperationsService {

    fun prettyJSONString(stringToTransform: String): String

    fun getAuthoritiesFromJSONString(stringToTransform: String): List<String>
}