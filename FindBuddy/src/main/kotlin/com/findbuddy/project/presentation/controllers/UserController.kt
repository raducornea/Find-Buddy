package com.findbuddy.project.presentation.controllers

import com.findbuddy.project.business.assemblers.UserAssembler
import com.findbuddy.project.business.assemblers.UserModelAssembler
import com.findbuddy.project.business.services.UserService
import com.findbuddy.project.data.entities.User
import com.findbuddy.project.data.models.UserModel
import com.findbuddy.project.persistence.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors

@RestController
class UserController {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userAssembler: UserAssembler

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var pagedResourcesAssembler: PagedResourcesAssembler<User>

    @Autowired
    private lateinit var userModelAssembler: UserModelAssembler

    @GetMapping("/users")
    fun all(): CollectionModel<EntityModel<User>> {
        val users = userService.getAllUsers().stream()
            .map { user: User -> userAssembler.toModel(user) }
            .collect(Collectors.toList())

        return CollectionModel.of(users, WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UserController::class.java).all()
        ).withSelfRel())
    }

    @GetMapping("/users/{id}")
    fun one(@PathVariable id: Int): EntityModel<User> {
        val user = userRepository.findById(id)
            .orElseThrow { Exception(id.toString()) }
        return userAssembler.toModel(user)
    }

    @PostMapping("/users")
    fun newUser(@RequestBody newUser: User): ResponseEntity<*> {
        val entityModel = userAssembler.toModel(userRepository.save(newUser))
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }

    @PutMapping("/users/{id}")
    fun replaceUser(@RequestBody newUser: User, @PathVariable id: Int): ResponseEntity<*> {
        val updatedUser = userRepository.findById(id)
            .map { user: User ->
                user.setUsername(newUser.getUsername())
                user.setPassword(newUser.getPassword())
                user.setEmail(newUser.getEmail())
                user.setFirstName(newUser.getFirstName())
                user.setLastName(newUser.getLastName())
                userRepository.save(user)
            }
            .orElseGet {
                newUser.setId(id)
                userRepository.save(newUser)
            }
        val entityModel = userAssembler.toModel(updatedUser)

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Int): ResponseEntity<*> {
        try {
            userRepository.deleteById(id)
            return ResponseEntity.noContent().build<Any>()
        } catch (ex: Exception) {
            return ResponseEntity.badRequest().body("Nu a mers delete la UserController")
        }
    }

    @GetMapping("/users/")
    fun filterAllUsers(
        @RequestParam(name = "username", defaultValue = "", required = false) username: Optional<String>,
        @RequestParam(name = "firstName", defaultValue = "", required = false) firstName: Optional<String>,
        @RequestParam(name = "lastName", defaultValue = "", required = false) lastName: Optional<String>,
        @RequestParam(name = "match", defaultValue = "partial", required = false) match: Optional<String>,
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "items_per_page", defaultValue = "1") items_per_page: Int,
    ): PagedModel<UserModel> {
        val pageable: Pageable = PageRequest.of(page, items_per_page);

        val filteredUsersPage: Page<User> = userService.getFilteredUsers(pageable, username, firstName, lastName, match)

        return pagedResourcesAssembler.toModel(filteredUsersPage, userModelAssembler)
    }
}