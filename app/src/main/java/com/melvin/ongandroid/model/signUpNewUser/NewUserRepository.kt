package com.melvin.ongandroid.model.signUpNewUser

import com.melvin.ongandroid.services.NewUserService
import javax.inject.Inject

class NewUserRepository @Inject constructor(private val newUserService: NewUserService) {
    suspend fun sendNewUser(newUser: NewUserBodyModel): Boolean{
        return newUserService.sendNewUser(newUser)
    }
}