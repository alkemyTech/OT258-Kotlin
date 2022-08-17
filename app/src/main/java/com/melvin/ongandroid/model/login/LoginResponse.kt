package com.melvin.ongandroid.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    var success: Boolean?,
    @SerializedName("data")
    var data: LoginResponseData?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("error")
    var error: String?
)

data class LoginResponseData(
    @SerializedName("token")
    var token: String?
)