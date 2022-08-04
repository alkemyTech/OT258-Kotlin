package com.melvin.ongandroid.model.signUpNewUser

import com.google.gson.annotations.SerializedName

// Response from POST(sendNewUser) - Register new user
data class NewUserResponse(
    @SerializedName("success") var success: Boolean
    //@SerializedName("data") var data: List<String>,
    //@SerializedName("message") var message: String
)
