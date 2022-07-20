package com.melvin.ongandroid.model.slides

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Slide data model of the API response
@Parcelize
data class SlidesDataModel(
    @SerializedName ("id") var id: Int?,
    @SerializedName ("name") var title: String?,
    @SerializedName ("description") var description: String?,
    @SerializedName ("image") var image: String?,
    @SerializedName ("order") var order: Int?,
    @SerializedName ("user_id") var user_id: Int?,
    @SerializedName ("created_at") var created_at: String?,
    @SerializedName ("updated_at") var updated_at: String?,
    @SerializedName ("deleted_at") var deleted_at: String?,
    @SerializedName ("group_id") var group_id: Int?
) : Parcelable {
    constructor(): this(null, "", "", "", null, null, "", "", "", null)
}

