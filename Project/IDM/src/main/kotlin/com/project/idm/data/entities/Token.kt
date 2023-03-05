package com.project.idm.data.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "tokens")
class Token() {

    @Id
    @Column(name = "uuid", nullable = false, unique = true)
    private var _uuid: String = ""
    private var token: String = ""
    private var userId: Int = 0
    private var expiryDate: Date = Date()
    private var revocationFlag: Boolean = false

    constructor(uuid: String, token: String, userId: Int, expiryDate: Date, revocationFlag: Boolean) : this() {
        this._uuid = uuid
        this.token = token
        this.userId = userId
        this.expiryDate = expiryDate
        this.revocationFlag = revocationFlag
    }

    fun getUuid(): String = _uuid
    fun getToken(): String = token
    fun getUserId(): Int = userId
    fun getExpiryDate(): Date = expiryDate
    fun getRevocationFlag(): Boolean = revocationFlag

    fun setId(uuid: String) {
        this._uuid = uuid
    }
    fun setName(token: String) {
        this.token = token
    }
    fun setUserId(userId: Int) {
        this.userId = userId
    }
    fun setExpiryDate(expiryDate: Date) {
        this.expiryDate = expiryDate
    }
    fun setRevocationFlag(revocationFlag: Boolean) {
        this.revocationFlag = revocationFlag
    }
}