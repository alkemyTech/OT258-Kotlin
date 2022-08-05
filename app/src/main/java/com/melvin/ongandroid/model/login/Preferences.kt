package com.melvin.ongandroid.model.login

import android.content.Context

class Preferences(val context: Context) {

        val keyToken: String = " "
    val storage = context.getSharedPreferences("ONGAndroid", Context.MODE_PRIVATE)

    fun saveToken(user: String) {
        storage.edit().putString(this.keyToken, user).apply()
    }
}