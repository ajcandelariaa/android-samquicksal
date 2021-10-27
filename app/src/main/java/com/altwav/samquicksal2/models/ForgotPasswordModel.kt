package com.altwav.samquicksal2.models

data class ForgotPasswordModel(
    val emailAddress: String? = null,
)

data class ForgotPasswordModelResponse(
    val status: String? = null,
)
