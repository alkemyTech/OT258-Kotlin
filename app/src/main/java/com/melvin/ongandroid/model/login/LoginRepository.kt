package com.melvin.ongandroid.model.login

import com.melvin.ongandroid.model.APIResponse
import com.melvin.ongandroid.services.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginService: LoginService) {
    suspend fun login(email: String, password: String): APIResponse<LoginResponse, Unit> {
        return loginService.login(email, password)
    }
}