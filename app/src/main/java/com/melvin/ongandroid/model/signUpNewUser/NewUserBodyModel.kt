package com.melvin.ongandroid.model.signUpNewUser

import com.google.gson.annotations.SerializedName

// Body required from POST(sendNewUser) - Register new user
data class NewUserBodyModel(
    @SerializedName("name") var nameNewUser: String ="",
    @SerializedName("email") var emailNewUser: String = "",
    @SerializedName("password") var passwordNewUser: String = ""
)
