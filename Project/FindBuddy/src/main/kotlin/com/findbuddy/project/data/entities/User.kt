package com.findbuddy.project.data.entities

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class User() {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private var _id: Int = 0
    @Column(name = "username", nullable = false, unique = true)
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
    fun setUsername(username: String?){
        this.username = username
    }
    fun setPassword(password: String?){
        this.password = password
    }
    fun setEmail(email: String?){
        this.email = email
    }
    fun setFirstName(firstName: String?){
        this.firstName = firstName
    }
    fun setLastName(lastName: String?){
        this.lastName = lastName
    }
    fun setRoleType(roleType: String?){
        this.roleType = roleType
    }

    constructor(
        id: Int,
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
    ) : this() {
        this._id = id
        this.username = username
        this.password = password
        this.email = email
        this.firstName = firstName
        this.lastName = lastName

        // this field must be on default User. userType can only be changed, but not initialized
        this.roleType = "User"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return (Objects.equals(_id, other._id) &&
                Objects.equals(username, other.username) &&
                Objects.equals(password, other.password) &&
                Objects.equals(email, other.email) &&
                Objects.equals(firstName, other.firstName) &&
                Objects.equals(lastName, other.lastName) &&
                Objects.equals(roleType, other.roleType))
    }

    override fun hashCode(): Int {
        return Objects.hash(_id, username, password, email, firstName, lastName, roleType)
    }

    override fun toString(): String {
        return "User{id=$_id, username='$username', password='$password', email='$email', firstName='$firstName', lastName='$lastName', roleType='$roleType'}"
    }
}