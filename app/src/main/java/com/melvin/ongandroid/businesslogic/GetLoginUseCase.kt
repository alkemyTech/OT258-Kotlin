package com.melvin.ongandroid.businesslogic

import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.model.login.Login
import com.melvin.ongandroid.services.ApiClient
import javax.inject.Inject


class GetLoginUseCase @Inject constructor(
        private val loginRepositorio: ApiClient,
        private val firebaseAnalytics: FirebaseAnalytics

) {
        suspend fun login(email: String, password: String) = loginRepositorio.getLogin(Login(email, password))
}