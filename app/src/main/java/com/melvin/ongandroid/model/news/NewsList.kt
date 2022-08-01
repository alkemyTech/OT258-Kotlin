package com.melvin.ongandroid.model.news

import com.google.gson.annotations.SerializedName

data class NewsList (
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("content") val content: String,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("deleted_at") val deleted_at: String

)