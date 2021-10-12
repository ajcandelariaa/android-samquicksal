package com.altwav.samquicksal2.models

data class LoginCustomerModel(
    val emailAddress: String? = null,
    val password: String? = null,
)

data class LoginCustomerModelResponse(
    val id: Int? = null,
    val status: String? = null,
)
