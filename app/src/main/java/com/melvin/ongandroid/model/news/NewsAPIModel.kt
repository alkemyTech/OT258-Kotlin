package com.melvin.ongandroid.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class NewsAPIModel(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("content")
    var content: String?,
    @SerializedName("image")
    var image: String,
    @SerializedName("user_id")
    var userId: Int?,
    @SerializedName("category_id")
    var categoryId: Int?,
    @SerializedName("created_at")
    var createdAt: Date?,
    @SerializedName("updated_at")
    var updatedAt: Date?,
    @SerializedName("deleted_at")
    var deletedAt: Date?,
) : Parcelable {
    constructor() : this(-1, "", "", "", "", null, null, null, null, null)
}

//Mapper to abstract API model from actual entity model
fun NewsAPIModel.asNewsModel(): NewsModel {
    return NewsModel(
        id = id ?: -1,
        name = name ?: "",
        content = content ?: "",
        imageUrl = image ?: ""
    )
}