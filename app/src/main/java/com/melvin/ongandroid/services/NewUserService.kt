package com.melvin.ongandroid.services

import com.melvin.ongandroid.model.APIResponse
import com.melvin.ongandroid.model.signUpNewUser.NewUserBodyModel
import com.melvin.ongandroid.model.signUpNewUser.NewUserResponse
import com.melvin.ongandroid.model.signUpNewUser.NewUserResponseError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class NewUserService @Inject constructor(private val apiClient: ApiClient) {
    suspend fun sendNewUser(newUser: NewUserBodyModel): APIResponse<NewUserResponse, NewUserResponseError> {
        val response = apiClient.sendNewUser(newUser)
        response.let {
            return if (it.code() == 422) {
                val errorBody = Json.decodeFromString<NewUserResponseError>(it.errorBody()!!.string())
                APIResponse(
                    success = false,
                    data = null,
                    error = errorBody,
                )
            } else {
                APIResponse(
                    success = true,
                    data = it.body(),
                    error = null,
                )
            }
        }
    }
}