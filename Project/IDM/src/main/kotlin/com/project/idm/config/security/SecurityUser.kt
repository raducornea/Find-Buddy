package com.project.idm.config.security;

import com.project.idm.data.entities.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class SecurityUser() : UserDetails {

    private lateinit var user: User

    constructor(user: User) : this() {
        this.user = user
    }

    // GrantedAuthority - contract for both Authorities / Roles
    override fun getAuthorities(): Collection<GrantedAuthority> {

        // (authorities (read, write, delete, execute) / roles (admin, manager, client, visitor))
        return user.getAuthorities() // get granted actions one can do
            .stream()
            .map { SecurityAuthority(it) }
            .collect(Collectors.toList()) // List.of(() -> "read");
    }

    override fun getPassword(): String = user.getPassword()
    override fun getUsername(): String = user.getUsername()

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
