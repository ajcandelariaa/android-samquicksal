package com.altwav.samquicksal2.models

data class RegisterCustomerModel(
    val name: String? = null,
    val emailAddress: String? = null,
    val contactNumber: String? = null,
    val password: String? = null,
    val deviceToken: String? = null,
)

data class RegisterCustomerModelResponse(
    val id: Int? = null,
    val status: String? = null,
)
