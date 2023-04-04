package com.project.idm.data.dtos

/**
 * UserModel contains Login Details + Profile Details (for ProfileService, used later)
 */
class UserDTO {

    private var username: String = ""
    private var password: String = ""
    private var passwordConfirm: String = ""
    private var firstName: String = ""
    private var lastName: String = ""
    private var email: String = ""

    fun getUsername(): String = username
    fun getPassword(): String = password
    fun getPasswordConfirm(): String = passwordConfirm
    fun getFirstName(): String = firstName
    fun getLastName(): String = lastName
    fun getEmail(): String = email

    fun setUsername(username: String) {
        this.username = username
    }
    fun setPassword(password: String) {
        this.password = password
    }
    fun setPasswordConfirm(passwordConfirm: String) {
        this.passwordConfirm = passwordConfirm
    }
    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }
    fun setLastName(lastName: String) {
        this.lastName = lastName
    }
    fun setEmail(email: String) {
        this.email = email
    }

}