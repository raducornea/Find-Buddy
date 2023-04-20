package com.project.idm.business.services

import com.project.idm.data.entities.Authority
import com.project.idm.data.entities.User
import com.project.idm.data.dtos.UserDTO
import com.project.idm.persistence.repositories.AuthorityRepository
import com.project.idm.persistence.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate


@Service
//class UserValidatorService : IUserValidatorService {
class UserValidatorService  {


    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var authorityRepository: AuthorityRepository

    fun checkUserFieldsValidity(userDTO: UserDTO): Boolean {

        val username = userDTO.getUsername()
        val password = userDTO.getPassword()
        val passwordConfirm = userDTO.getPasswordConfirm()
        val preferences = userDTO.getPreferences()

        println("Preferences size: ${preferences.size}")

        val validUsername = username.length in 2..20
        val validPassword = password == passwordConfirm && password.length > 2
        val validPreferences = preferences.size in 0..10
        val validFields = validUsername && validPassword && validPreferences

        return validFields
    }

    fun isUserRegisterValid(userDTO: UserDTO): Boolean {

        // 1. validate fields
        val validFields = checkUserFieldsValidity(userDTO)
        if (!validFields) return false

        // IMPORTANT: can't insert user before making sure in both DBs we can insert it!
        // that's why we check before posting
        val username = userDTO.getUsername()

        // 2. check if it's already in db (IDM Service)
        val userFound = userRepository.findUserByUsername(username)
        if (userFound.isPresent) return false

        // after that, we can build the user for IDM, but NOT post it YET
        val passwordEncoder = BCryptPasswordEncoder()
        val hashedPassword = passwordEncoder.encode(userDTO.getPassword())

        val authorities = mutableSetOf<Authority>()
        val readAuthority = authorityRepository.findAuthorityByName("read")
        authorities.add(readAuthority)

        val user = User()
        user.setUsername(userDTO.getUsername())
        user.setPassword(hashedPassword)
        user.setAuthorities(authorities)

        // NOW WE MUST post in IDM Service, to get its id
        userRepository.save(user)

        // 3. try posting the user profile in the Profile Service
        // we NEED to set the id, so we can find it easily through tables
        userDTO.setIdmId(userRepository.findUserByUsername(user.getUsername()).get().getId())
        val requestUrl = "http://localhost:8002/profile/new-profile"
        val response = sendRequest(requestUrl, userDTO)
        if (response == null || response.statusCode != HttpStatus.CREATED) {
            userRepository.delete(user)
            return false
        }

        return true
    }

    private fun sendRequest(url: String, userDTO: UserDTO): ResponseEntity<String>? {
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity(userDTO, headers)

        return try {
            restTemplate.exchange(url, HttpMethod.POST, request, String::class.java)
        } catch (ex: HttpClientErrorException) {
            ResponseEntity.status(ex.statusCode).body(ex.responseBodyAsString)
        } catch (ex: Exception) {
            null
        }
    }

}