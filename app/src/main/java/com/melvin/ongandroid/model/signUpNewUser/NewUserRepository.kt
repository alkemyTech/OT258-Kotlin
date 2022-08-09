package com.melvin.ongandroid.model.signUpNewUser

import com.melvin.ongandroid.model.APIResponse
import com.melvin.ongandroid.services.NewUserService
import javax.inject.Inject

class NewUserRepository @Inject constructor(private val newUserService: NewUserService) {
    suspend fun sendNewUser(newUser: NewUserBodyModel): APIResponse<NewUserResponse, NewUserResponseError>{
        return newUserService.sendNewUser(newUser)
    }
}