package com.altwav.samquicksal2.models

data class GeofencingModel(
    var cust_id: Int? = null,
    var cust_lat: String? = null,
    var cust_long: String? = null,
)


data class GeofencingModelResponse(
    var status: String? = null,
)
