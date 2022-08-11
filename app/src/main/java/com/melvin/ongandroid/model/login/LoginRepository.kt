package com.melvin.ongandroid.model.login

import com.melvin.ongandroid.businesslogic.GetLoginUseCase
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginUseCase: GetLoginUseCase
)
{
    suspend fun login(email: String, password: String) = loginUseCase.login(email, password)

}