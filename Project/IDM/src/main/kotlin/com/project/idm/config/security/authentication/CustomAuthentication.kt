//package com.project.idm.config.security.authentication
//
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.GrantedAuthority
//
//class CustomAuthentication(private var authentication: Boolean = false, private var key: String? = null) : Authentication {
//
////    constructor(authentication: Boolean, key: String) : this() {
////        this.authentication = authentication
////        this.key = key
////    }
//
//    fun getAuthentication(): Boolean = authentication
//    fun getKey(): String? = key
//
//    fun setAuthentication(authentication: Boolean){
//        this.authentication = authentication
//    }
//    fun setKey(key: String?){
//        this.key = key
//    }
//
//    override fun isAuthenticated(): Boolean = authentication
//
//
//
//
//    @Throws(IllegalArgumentException::class)
//    override fun setAuthenticated(isAuthenticated: Boolean) { }
//
//    override fun getAuthorities(): Collection<GrantedAuthority>? = null
//    override fun getCredentials(): Any? = null
//    override fun getDetails(): Any? = null
//    override fun getPrincipal(): Any? = null
//    override fun getName(): String? = null
//}
