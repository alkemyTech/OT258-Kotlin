package com.melvin.ongandroid.model.contact

import com.google.gson.annotations.SerializedName

data class ContactDataModel(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("message") val message: String = "",
)