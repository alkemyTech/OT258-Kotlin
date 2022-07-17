package com.melvin.ongandroid.model.testimonials

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DataModel(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("created_at")
    var createdAt: Date?,
    @SerializedName("updated_at")
    var updatedAt: Date?,
    @SerializedName("deleted_at")
    var deletedAt: Date?,
    @SerializedName("group_id")
    var groupId: Int?
) : Parcelable {
    constructor(): this(null,"","","",null,null,null,null)
}
