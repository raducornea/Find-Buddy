package com.project.gateway.business.interfaces

import java.net.HttpURLConnection

interface IUrlCallerService {

    fun getTokenResponseBody(url: String, token: String): Any

    fun getResponseBody(connection: HttpURLConnection): Any
}