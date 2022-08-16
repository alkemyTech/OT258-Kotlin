package com.melvin.ongandroid.model.login

import android.content.Context
import javax.inject.Inject


class LoginPreferences  @Inject constructor(val context: Context) {

    val keyToken = "user_token"
    val storage = context.getSharedPreferences("ONGAndroid", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        storage.edit().putString(this.keyToken, token).apply()
    }

    fun getToken(): String? {
        return storage.getString(this.keyToken, null)
    }

    fun clear() {
        storage.edit().clear().apply()
    }

}