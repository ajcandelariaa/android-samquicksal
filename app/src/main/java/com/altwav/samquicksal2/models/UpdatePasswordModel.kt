package com.altwav.samquicksal2.models

data class UpdatePasswordModel(
    val cust_id: Int? = null,
    val oldPassword: String? = null,
    val newPassword: String? = null,
)

data class UpdatePasswordModelResponse(
    val status: String? = null,
)