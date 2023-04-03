package com.project.gateway.business.services

import com.google.gson.*
import com.project.gateway.business.interfaces.IJSONOperationsService
import org.springframework.stereotype.Service
import java.util.*


@Service
class JSONOperationsService : IJSONOperationsService {

    override fun prettyJSONString(stringToTransform: String): String {

        // code might crash, so it's better to return something rather than letting it crash
        try {
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val jsonElement: JsonElement = JsonParser.parseString(stringToTransform)
            val prettyJsonString: String = gson.toJson(jsonElement)
            return prettyJsonString

        } catch (exception: Exception) {
            return stringToTransform
        }
    }

    override fun getAuthoritiesFromJSONString(stringToTransform: String): List<String> {

        // code might crash, so it's better to return something rather than letting it crash
        try {
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val jsonObject: JsonObject = gson.fromJson(stringToTransform, JsonObject::class.java)

            val jsonElement: JsonElement = jsonObject.getAsJsonArray("authorities")
            val authoritiesArray: Array<String> = gson.fromJson(jsonElement, Array<String>::class.java)

            val authoritiesList: List<String> = authoritiesArray.asList()
            return authoritiesList

        } catch (exception: Exception) {
            return listOf()
        }
    }
}