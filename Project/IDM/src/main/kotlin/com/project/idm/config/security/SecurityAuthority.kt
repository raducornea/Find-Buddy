package com.project.idm.config.security;


import com.project.idm.data.entities.Authority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority

class SecurityAuthority(private val authority: Authority) : GrantedAuthority {

    override fun getAuthority(): String = authority.getName()
}
