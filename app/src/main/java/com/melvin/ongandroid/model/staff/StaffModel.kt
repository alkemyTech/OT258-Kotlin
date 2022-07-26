package com.melvin.ongandroid.model.staff

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class StaffModel(
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("data")
    var data: @RawValue List<StaffDataModel>,
    @SerializedName("message")
    var message: String?
) : Parcelable {
    constructor() : this(false, emptyList(), "")
}