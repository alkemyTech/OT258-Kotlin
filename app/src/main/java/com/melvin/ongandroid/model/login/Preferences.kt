package com.melvin.ongandroid.model.login

import android.content.Context
import javax.inject.Inject


class Preferences  @Inject constructor(val context: Context) {

    val keyToken = "user_token"
    val storage = context.getSharedPreferences("ONGAndroid", Context.MODE_PRIVATE)

    fun saveToken(user: String) {
        storage.edit().putString(this.keyToken, user).apply()
    }

    fun getToken(): String {
        return storage.getString(this.keyToken, " ")!!
    }


}