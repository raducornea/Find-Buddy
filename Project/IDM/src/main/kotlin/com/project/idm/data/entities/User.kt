package com.project.idm.data.entities

import jakarta.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id = 0
    private var username: String = ""
    private var password: String = ""
    @Transient
    private var passwordConfirm: String = ""

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "users_authorities",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")]
    )
    private var authorities: Set<Authority> = setOf()

    fun getId(): Int = _id
    fun getUsername(): String = username
    fun getPassword(): String = password
    fun getPasswordConfirm(): String = passwordConfirm
    fun getAuthorities(): Set<Authority> = authorities

    fun setId(id: Int) {
        this._id = id
    }
    fun setUsername(username: String) {
        this.username = username
    }
    fun setPassword(password: String) {
        this.password = password
    }
    fun setPasswordConfirm(passwordConfirm: String) {
        this.passwordConfirm = passwordConfirm
    }
    fun setAuthorities(authorities: Set<Authority>) {
        this.authorities = authorities
    }
}