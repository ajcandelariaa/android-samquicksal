package com.altwav.samquicksal2.models

data class DeviceTokenModel(
    val cust_id: Int? = null,
    val deviceToken: String? = null,
)

data class DeviceTokenModelResponse(
    val status: String? = null,
)