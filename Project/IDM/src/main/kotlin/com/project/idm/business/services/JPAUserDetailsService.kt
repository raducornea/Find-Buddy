package com.project.idm.business.services

import com.project.idm.data.entities.User
import com.project.idm.persistence.repositories.UserRepository
import com.project.idm.config.security.SecurityUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Se implementeaza interfata UserDetailsService
 * -> obtine detalii de la baza de date cu datele de loggare pentru utilizatori
 * -> contractul Spring Security, prin care Spring intelege cum sunt transmise datele de utilizator
 * -> astfel, nu va mai fi folosit cel cu parolele random la pornirea aplicatiei
 */
@Service
class JPAUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    /**
     * Se returneaza un obiect de tip UserDetails
     * -> se obtin utilizatori existenti
     * -> eroare, in cazul in care nu exista
     */
    override fun loadUserByUsername(username: String): UserDetails {
        val userFound = userRepository.findUserByUsername(username)

        return userFound
            .map { user: User -> SecurityUser(user) } // transform the user into SecurityUser
            .orElseThrow { UsernameNotFoundException("Username not found $username") }
    }
}
