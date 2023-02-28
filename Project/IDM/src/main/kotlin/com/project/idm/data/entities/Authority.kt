package com.project.idm.data.entities

import jakarta.persistence.*

@Entity
@Table(name = "authorities")
class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id = 0
    private var name: String = ""

    @ManyToMany(mappedBy = "authorities")
    private var users: Set<User> = setOf()

    fun getId(): Int = _id
    fun getName(): String = name
    fun getUsers(): Set<User> = users

    fun setId(id: Int){
        this._id = id
    }
    fun setName(name: String){
        this.name = name
    }
    fun setUsers(users: Set<User>){
        this.users = users
    }
}