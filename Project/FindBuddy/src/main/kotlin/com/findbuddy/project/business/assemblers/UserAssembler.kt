package com.findbuddy.project.business.assemblers

import com.findbuddy.project.data.entities.User
import com.findbuddy.project.presentation.controllers.UserController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Component

@Component
class UserAssembler : RepresentationModelAssembler<User, EntityModel<User>> {
    override fun toModel(user: User): EntityModel<User> {
        return EntityModel.of(
            user,
            linkTo(WebMvcLinkBuilder.methodOn(UserController::class.java).one(user.getId())).withSelfRel(),
            linkTo(WebMvcLinkBuilder.methodOn(UserController::class.java).all()).withRel("users")
        )
    }
}