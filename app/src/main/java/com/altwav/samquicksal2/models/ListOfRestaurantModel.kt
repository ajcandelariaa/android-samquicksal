package com.altwav.samquicksal2.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListOfRestaurantModel (
    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("rName")
    @Expose
    val rName: String? = null,

    @SerializedName("rAddress")
    @Expose
    val rAddress: String? = null,

    @SerializedName("rLogo")
    @Expose
    val rLogo: String? = null,

    @SerializedName("rSchedule")
    @Expose
    val rSchedule: String? = null
)

