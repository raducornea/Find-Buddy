package com.project.idm.data.models

/**
 * UserModel contains Login Details + Profile Details (for ProfileService, used later)
 */
class UserModel {

    private var username: String = ""
    private var password: String = ""
    private var passwordConfirm: String = ""

    fun getUsername(): String = username
    fun getPassword(): String = password
    fun getPasswordConfirm(): String = passwordConfirm

    fun setUsername(username: String) {
        this.username = username
    }
    fun setPassword(password: String) {
        this.password = password
    }
    fun setPasswordConfirm(passwordConfirm: String) {
        this.passwordConfirm = passwordConfirm
    }
}