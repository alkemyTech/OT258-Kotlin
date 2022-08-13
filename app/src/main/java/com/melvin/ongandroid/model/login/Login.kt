package com.melvin.ongandroid.model.login

import com.google.gson.annotations.SerializedName

data class Login (
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password :String

)

data class Token (
    @SerializedName("token")
    var token: String
    )