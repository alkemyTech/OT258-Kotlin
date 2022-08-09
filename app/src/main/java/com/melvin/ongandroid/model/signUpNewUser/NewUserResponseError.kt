package com.melvin.ongandroid.model.signUpNewUser

import kotlinx.serialization.Serializable

@Serializable
data class NewUserResponseError (
    val message: String,
    val errors: NewUserResponseErrorBody,

    )
@Serializable
data class NewUserResponseErrorBody (
    val name: List<String>? = null,
    val email: List<String>? = null,
    val password: List<String>? = null,
)