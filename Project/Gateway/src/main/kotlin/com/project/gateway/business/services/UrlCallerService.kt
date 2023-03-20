package com.project.gateway.business.services

import com.project.gateway.business.interfaces.IUrlCallerService
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Service
class UrlCallerService : IUrlCallerService {

    override fun getTokenResponseBody(url: String, token: String): Any {

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Cookie", "cookieAuthorizationToken=$token")

        return getResponseBody(connection)
    }

    override fun getResponseBody(connection: HttpURLConnection): Any {

        if (connection.responseCode == 500){
            return "Resource on ${connection.url} not available."
        }

        try {
            val inputStream = connection.inputStream
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            val response = StringBuilder()
            var line = bufferedReader.readLine()
            while (line != null) {
                response.append(line)
                line = bufferedReader.readLine()
            }

            bufferedReader.close()
            inputStreamReader.close()
            inputStream.close()

            return response.toString()

        } catch(_: Exception) {
            return "Service on ${connection.url} not available."
        }
    }
}