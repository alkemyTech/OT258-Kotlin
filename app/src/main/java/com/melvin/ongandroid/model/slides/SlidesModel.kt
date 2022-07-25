package com.melvin.ongandroid.model.slides

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

// Response data model of the API -> https://ongapi.alkemy.org/api/slides
@Parcelize
data class SlidesModel(
    @SerializedName ("success") var success: Boolean,
    @SerializedName ("data")  var data: @RawValue List<SlidesDataModel>,
    @SerializedName ("message") var message: String,
) : Parcelable {
    constructor():this(false, emptyList(), "")
}
