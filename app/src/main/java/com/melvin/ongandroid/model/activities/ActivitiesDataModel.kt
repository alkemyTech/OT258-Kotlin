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
    @SerializedName("description")
    var description: String?,
    @SerializedName("image")
    var image: String?,
) : Parcelable {
    constructor() : this(null, "", "", "")
}


