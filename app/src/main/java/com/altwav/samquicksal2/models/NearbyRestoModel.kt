package com.altwav.samquicksal2.models

data class NearbyRestoModel(
    val cust_lat: String? = null,
    val cust_long: String? = null,
)


data class NearbyRestoModelResponse(
    val rest_id: Int? = null,
    val rName: String? = null,
    val rAddress: String? = null,
    val rLogo: String? = null,
    val rSchedule: String? = null,
    val distance: String? = null,
)
