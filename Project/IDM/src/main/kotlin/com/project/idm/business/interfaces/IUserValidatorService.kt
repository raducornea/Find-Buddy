package com.project.idm.business.interfaces

import com.project.idm.data.dtos.UserDTO

interface IUserValidatorService {

    fun checkUserFieldsValidity(userModel: UserDTO): String
}