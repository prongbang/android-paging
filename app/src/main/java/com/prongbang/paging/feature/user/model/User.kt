package com.prongbang.paging.feature.user.model
import android.os.Parcelable
import androidx.databinding.BaseObservable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("email")
    var email: String?,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("gender")
    var gender: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("ip_address")
    var ipAddress: String?,
    @SerializedName("last_name")
    var lastName: String?
) : Parcelable, BaseObservable()
