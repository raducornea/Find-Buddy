package com.project.profile.data.entities

import com.project.profile.data.dtos.UserDTO
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "profiles")
class UserProfile {

    @Id
    private var id: ObjectId = ObjectId()
    private var idmId: Int = 0
    private var firstName: String = ""
    private var lastName: String = ""
    private var email: String = ""

    constructor() { }
    constructor(userDTO: UserDTO) {
        this.id = ObjectId.get()
        this.idmId = userDTO.getIdmId()
        this.firstName = userDTO.getFirstName()
        this.lastName = userDTO.getLastName()
        this.email = userDTO.getEmail()
    }

    fun getId(): ObjectId = id
    fun getIdmId(): Int = idmId
    fun getFirstName(): String = firstName
    fun getLastName(): String = lastName
    fun getEmail(): String = email

    fun setId(id: ObjectId){
        this.id = id
    }
    fun setIdmId(idmId: Int){
        this.idmId = idmId
    }
    fun setFirstName(firstName: String){
        this.firstName = firstName
    }
    fun setLastName(lastName: String){
        this.lastName = lastName
    }
    fun setEmail(email: String){
        this.email = email
    }
}