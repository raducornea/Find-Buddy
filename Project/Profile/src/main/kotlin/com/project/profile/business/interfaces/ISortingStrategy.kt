package com.project.profile.business.interfaces

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.profile.data.entities.UserProfile
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

interface ISortingStrategy {

    fun sort(currentUser: UserProfile, users: List<UserProfile>): List<UserProfile>

    private fun postRequest(values: Any?, url: String): String {
        val objectMapper = ObjectMapper()
        val requestBody: String = objectMapper
            .writeValueAsString(values)

        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return response.body()
    }

    fun getMatchingUsersByStrategy(currentUser: UserProfile, users: List<UserProfile>, url: String): List<UserProfile> {

        val targetPreferences = currentUser.getPreferences()
        val usersPreferences = users.map { it.getPreferences() }
        val allUsersPreferences = (targetPreferences + usersPreferences.flatten()).distinct()

        val map = mutableMapOf<String, Int>()
        var counter = 1
        allUsersPreferences.forEach {
            map.put(it, counter++)
        }

        val targetPreferencesNUM = targetPreferences.map { map[it] }
        val usersPreferencesNUM = usersPreferences.map { it -> it.map { map[it] } }

        val jsonMap = mutableMapOf<String, List<Any?>>()
        jsonMap.put("target_preferences", targetPreferencesNUM)
        jsonMap.put("users_preferences", usersPreferencesNUM)

        // receive response
        val response = postRequest(jsonMap, url) // [4 5 6 0 1]

        val resultingUsers = response
            .removePrefix("[")
            .removeSuffix("]")
            .split(" ")
            .map { users[it.toInt()] }

        return resultingUsers
    }
}
