package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.APIResponse
import com.melvin.ongandroid.model.login.LoginResponse
import com.melvin.ongandroid.services.ApiClient
import javax.inject.Inject

class GetMeUseCase @Inject constructor(private val apiClient: ApiClient) {
    suspend operator fun invoke(token: String): APIResponse<LoginResponse, Unit> {
        val response = apiClient.getMe("Bearer $token")
        response.let {
            return APIResponse(
                success = it.body()?.success == true,
                data = null,
                error = null,
            )
        }
    }
}