package com.altwav.samquicksal2.models

data class AddFooditemModel(
    val cust_id: Int? = null,
    val foodItemId: Int? = null,
    val foodName: String? = null,
    val foodPrice: String? = null,
)

data class AddFooditemModelResponse(
    val status: String? = null,
)
