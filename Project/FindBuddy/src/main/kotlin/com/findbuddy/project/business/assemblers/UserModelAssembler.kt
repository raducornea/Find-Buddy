package com.findbuddy.project.business.assemblers

import com.findbuddy.project.data.entities.User
import com.findbuddy.project.data.models.UserModel
import com.findbuddy.project.presentation.controllers.UserController
import org.springframework.beans.BeanUtils

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component


@Component
class UserModelAssembler : RepresentationModelAssemblerSupport<User, UserModel>(
    UserController::class.java,
    UserModel::class.java
) {

    override fun toModel(entity: User): UserModel {
        val model = UserModel()
        BeanUtils.copyProperties(entity, model)
        return model
    }
}