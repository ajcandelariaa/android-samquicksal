package com.altwav.samquicksal2.models

data class AccountCustomerModel(
    val id: Int? = null,
)
data class AccountCustomerModelResponse(
    val name: String? = null,
    val emailAddress: String? = null,
    val emailAddressVerified: String? = null,
    val contactNumber: String? = null,
    val contactNumberVerified: String? = null,
    val password: String? = null,
    val profileImage: String? = null,
)