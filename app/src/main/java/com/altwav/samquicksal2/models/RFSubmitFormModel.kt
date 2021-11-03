package com.altwav.samquicksal2.models

data class RFSubmitFormModel(
    val cust_id: Int? = null,
    val rating: String? = null,
    val comment: String? = null,
    val anonymous: String? = null,
    val type: String? = null,
)

data class RFSubmitFormModelResponse(
    val status: String? = null,
)
