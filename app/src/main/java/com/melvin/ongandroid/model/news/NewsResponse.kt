package com.melvin.ongandroid.model.news

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<NewsList>,
    @SerializedName("message") val message: String
)