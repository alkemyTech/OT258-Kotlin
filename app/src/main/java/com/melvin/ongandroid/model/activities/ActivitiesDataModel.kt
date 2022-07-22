package com.melvin.ongandroid.model.activities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivitiesDataModel(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("user_id")
    var userId: String? = "",
    @SerializedName("category_id")
    var categoryId: Int? = 0,
    @SerializedName("created_at")
    var createdAt: String? = "",
    @SerializedName("updated_at")
    var updatedAt: String? = "",
    @SerializedName("deleted_at")
    var deletedAt: String? = "",
) : Parcelable {
    constructor() : this(null, "", "", "", null, "", null, "", "", "")
}


