//package com.project.idm.config.security.filters
//
//import com.project.idm.config.security.authentication.CustomAuthentication
//import com.project.idm.config.security.managers.CustomAuthenticationManager
//import jakarta.servlet.FilterChain
//import jakarta.servlet.ServletException
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.stereotype.Component
//import org.springframework.web.filter.OncePerRequestFilter
//import java.io.IOException
//
//// Custom Filter (First) // Manager (Second) // Provider (Third) - can use UDS (User Details Service) & PE (Password Encoder)
//@Component
//class CustomAuthenticationFilter(private val customAuthenticationManager: CustomAuthenticationManager) : OncePerRequestFilter() {
//
//
//    @Throws(ServletException::class, IOException::class)
//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//
//        // 1. create an authentication object which is not yet authenticated
//        val key: String? = request.getHeader("key")
//        val ca = CustomAuthentication(false, key)
//
//        // 2. delegate the authentication object to the manager
//        // 3. get back the authentication from the manager
//        val a: Authentication = customAuthenticationManager.authenticate(ca)
//
//        // 4. if the object is authenticated then send request to the next filter in the chain
//        if (a.isAuthenticated()) {
//            SecurityContextHolder.getContext().setAuthentication(a)
//
//            // propagate to the next filter in the filter chain
//            // only when the authentication works
//            filterChain.doFilter(request, response)
//        }
//    }
//}
