package com.findbuddy.project.data.models

import org.springframework.hateoas.RepresentationModel

class UserModel : RepresentationModel<UserModel>() {
    private var _id: Int = 0
    private var username: String? = null
    private var password: String? = null
    private var email: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var roleType: String? = null

    fun getId(): Int = _id
    fun getUsername(): String? = username
    fun getPassword(): String? = password
    fun getEmail(): String? = email
    fun getFirstName(): String? = firstName
    fun getLastName(): String? = lastName
    fun getRoleType(): String? = roleType

    fun setId(id: Int){
        this._id = id
    }
    fun setUsername(username: String){
        this.username = username
    }
    fun setPassword(password: String){
        this.password = password
    }
    fun setEmail(email: String){
        this.email = email
    }
    fun setFirstName(firstName: String){
        this.firstName = firstName
    }
    fun setLastName(lastName: String){
        this.lastName = lastName
    }
    fun setRoleType(roleType: String?){
        this.roleType = roleType
    }
}