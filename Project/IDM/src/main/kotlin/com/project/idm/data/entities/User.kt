package com.project.idm.data.entities

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id = 0
    private var username: String = ""
    private var password: String = ""

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_authorities",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")]
    )
    private var authorities: Set<Authority> = setOf()

    fun getId(): Int = _id
    fun getUsername(): String = username
    fun getPassword(): String = password
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
    fun setAuthorities(authorities: Set<Authority>) {
        this.authorities = authorities
    }
}