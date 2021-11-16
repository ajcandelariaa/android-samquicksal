package com.altwav.samquicksal2.models

data class UpdateNameModel(
    val cust_id: Int? = null,
    val infoType: String? = null,
    val input: String? = null,
)

data class UpdateNameModelResponse(
    val status: String? = null,
)