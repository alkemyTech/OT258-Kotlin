package com.melvin.ongandroid.model.activities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ActivitiesModel(
    @SerializedName("success") var success: Boolean,
    @SerializedName("data")  var data: @RawValue List<ActivitiesDataModel>,
    @SerializedName("message") var message: String,
) : Parcelable {
    constructor():this(false, emptyList(), "")
}


