package com.melvin.ongandroid.businesslogic

import com.melvin.ongandroid.model.APIResponse
import com.melvin.ongandroid.model.login.LoginRepository
import com.melvin.ongandroid.model.login.LoginResponse
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String): APIResponse<LoginResponse, Unit> {
        return loginRepository.login(email, password)
    }
}