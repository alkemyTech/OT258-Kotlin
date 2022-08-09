package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.login.Login
import com.melvin.ongandroid.model.login.Result
import com.melvin.ongandroid.services.ApiClient
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(
    private val apiClient: ApiClient
) : Result()

{
    suspend fun login(email: String, password: String) = getResult { apiClient.getLogin(Login(email, password))}

}