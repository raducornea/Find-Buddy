package com.project.idm.config.security;


import com.project.idm.data.entities.Authority
import org.springframework.security.core.GrantedAuthority

class SecurityAuthority() : GrantedAuthority {

    private lateinit var authority: Authority

    constructor(authority: Authority) : this() {
        this.authority = authority
    }

    override fun getAuthority(): String = authority.getName()
}
