package com.project.idm.business.interfaces

import com.project.idm.data.models.UserModel

interface IUserValidatorService {

    fun checkUserFieldsValidity(userModel: UserModel): String
}