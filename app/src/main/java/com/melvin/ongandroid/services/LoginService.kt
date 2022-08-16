package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.APIResponse
import com.melvin.ongandroid.model.login.LoginBody
import com.melvin.ongandroid.model.login.LoginResponse
import javax.inject.Inject

class LoginService @Inject constructor(private val apiClient: ApiClient) {
    suspend fun login(email: String, password: String): APIResponse<LoginResponse, Unit> {
        val response = apiClient.login(LoginBody(email, password))
        response.let {
            return if (it.body()?.success == true) {
                APIResponse(
                    success = true,
                    data = it.body(),
                    error = null,
                )
            } else {
                APIResponse(
                    success = false,
                    data = null,
                    error = null
                )

            }
        }
    }
}
