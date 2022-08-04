package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.signUpNewUser.NewUserBodyModel
import javax.inject.Inject

class NewUserService @Inject constructor(private val apiClient: ApiClient) {
    suspend fun sendNewUser(newUser: NewUserBodyModel): Boolean{
        return apiClient.sendNewUser(newUser).body()?.success ?: false
    }
}