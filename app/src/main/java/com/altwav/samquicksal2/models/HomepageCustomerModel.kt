package com.altwav.samquicksal2.models

data class HomepageCustomerModel(
    val id: Int? = null,
)

data class HomepageCustomerModelResponse(
    val name: String? = null,
    val emailAddress: String? = null,
    val profileImage: String? = null,
    val status: String? = null,
)